package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.services;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.wexinc.gf.ifcs.ms.spring.*;
import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects.InterfaceFilesIncomingEntity;
import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories.ie.MicroServicesRepository;
import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories.ifcs.MClientRepository;
import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories.ifcs.SundryRepository;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@ComponentScan("com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories")
public class SundryService {

    @Autowired
    private final SundryRepository sundryRepository;
    private final MClientRepository mClientRepository;
    private final MicroServicesRepository microServicesRepository;
    private final CamelContext camelContext;

    private static final Logger logger = LoggerFactory.getLogger(SundryService.class);

    @Autowired
    public SundryService(SundryRepository sundryRepository, MClientRepository mClientRepository,
                         CamelContext camelContext,
                         MicroServicesRepository microServicesRepository){
        this.sundryRepository = sundryRepository;
        this.mClientRepository = mClientRepository;
        this.camelContext = camelContext;
        this.microServicesRepository = microServicesRepository;
    }



    public ResultType readCsvFile(Path source, BulkIndicatorRequest request){
        ResultType resultType = new ResultType();
        resultType.setIsError(false);
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();                                                   //C:\Users\w505593\ifcs-wfe-ms-load-sundry\src\main\resources  ${project.basedir}
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(source.toString())).withSkipLines(1).withCSVParser(parser).build()) {
            List<String[]> r = reader.readAll();
            LoadSundryAdjustmentTransactions loadSundryAdjustmentTransactions = new LoadSundryAdjustmentTransactions();
            Header header = new Header();

            String countryCode = source.getFileName().toString().split("_")[1];
            Long clientMID = mClientRepository.findClientMid(countryCode);

            InterfaceFilesIncomingEntity interface_record = null;
            Path destinationFolder = null;
            String sequenceNumber = null;
            try {
                interface_record = sundryRepository.interfaceIncomingRows(clientMID);
                destinationFolder = Paths.get(interface_record.getInputFileFolderName());
                sequenceNumber = Long.toString(interface_record.getLastSequenceNumber() + 1L);
            }catch (Exception e){
                logger.error(e.toString());
            }
            int initialLength = sequenceNumber.length();
            for(int i=0;i<6-initialLength;i++){
                sequenceNumber = 0+sequenceNumber;
            }
            header.setSequenceNumber(sequenceNumber);

            //String directory = "src/main/resources/";




            String fileNameNoExt = "LoadSundryAdjustmentTransactions_"+clientMID+"_"+header.getSequenceNumber();

            request.getPayloadDetails().setFileName(fileNameNoExt+".zip");
            request.getPayloadDetails().setDirectory(destinationFolder.toString());
            request.getMetaData().setMessageId("LoadSundry");

            header.setFileName(fileNameNoExt+".xml");
            header.setCountryCode(countryCode);

            int recordCount = r.size();
            /*If CSV has one empty line at the bottom*/
            if(r.get(r.size()-1).length==0){
                recordCount--;
            }
            header.setRecordCount(Integer.toString(recordCount));

            header.setExternalSupplier("WFE_"+countryCode);
            header.setStartTime(LocalDateTime.now().toString().split("T")[1].substring(0, 8));
            header.setStartDate(LocalDateTime.now().toString().split("T")[0]);
            loadSundryAdjustmentTransactions.setHeader(header);

            r.forEach(x ->

                        {
                            int count = 0;
                            for(String s : x){
                                if(count<9 && s.trim().isEmpty()){
                                    resultType.setIsError(true);
                                    logger.error("Value in row is empty");
                                }
                                count++;
                            }
                            try {
                                LoadSundryAdjustmentTransaction loadSundryAdjustmentTransaction = new LoadSundryAdjustmentTransaction();
                                SundryAdjustment sundryAdjustment = new SundryAdjustment();
                                sundryAdjustment.setCustomerAccountNumber(x[1]);
                                sundryAdjustment.setAdjustmentTypeCode(x[2]);

                                SimpleDateFormat asRead = new SimpleDateFormat("dd/MM/yy");
                                SimpleDateFormat gregorian = new SimpleDateFormat("yyyy-MM-dd");
                                Date effectiveOn = asRead.parse(x[3]);
                                String effectiveOnGregorian = gregorian.format(effectiveOn);
                                sundryAdjustment.setEffectiveOn(effectiveOnGregorian.toString());

                                sundryAdjustment.setSundryTypeCode(x[4]);
                                sundryAdjustment.setAmount(x[5]);
                                sundryAdjustment.setReference(x[6]);
                                sundryAdjustment.setGLAccountCode(x[7]);
                                sundryAdjustment.setGLDescription(x[8]);

                                loadSundryAdjustmentTransaction.setSundryAdjustment(sundryAdjustment);

                                loadSundryAdjustmentTransactions.getLoadSundryAdjustmentTransaction().add(loadSundryAdjustmentTransaction);

                            } catch (ParseException e) {
                                resultType.setIsError(true);
                                e.printStackTrace();
                                logger.error("Effective On date is in wrong format; should be dd/MM/yy");
                            }catch(Exception e){
                                resultType.setIsError(true);
                                logger.error("Csv row is invalid - cannot retrieve details");
                            }
                        }

                );
            if(resultType.isIsError()){
                return resultType;
            }
                //marshall java object to xml
            try{
                JAXBContext jaxbContext = JAXBContext.newInstance(LoadSundryAdjustmentTransactions.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                JAXBElement jaxbElement = new JAXBElement(new QName("","loadSundryAdjustmentTransactions"),
                        LoadSundryAdjustmentTransactions.class, loadSundryAdjustmentTransactions);

                System.out.println("File created at: " + destinationFolder.resolve(fileNameNoExt+".xml"));
                jaxbMarshaller.marshal(jaxbElement, destinationFolder.resolve(fileNameNoExt+".xml").toFile());
                zipFile(fileNameNoExt, destinationFolder.toString());

                //Delete File
                File file = new File(destinationFolder.resolve(fileNameNoExt+".xml").toFile().toString());
                file.delete();
            } catch (Exception e) {
                logger.error(e.toString());
                return resultType;
            }
        } catch (IOException e) {
            resultType.setIsError(true);
            e.printStackTrace();
            return resultType;
        } catch (CsvException e) {
            resultType.setIsError(true);
            e.printStackTrace();
            return resultType;
        }
        return resultType;
    }


    public void zipFile(String fileNameNoExt, String destinationFolder){

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        Path destination = Paths.get(destinationFolder).resolve(fileNameNoExt);
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(destination.toString()+".zip");
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            File input = new File(destination.toString()+".xml");

            fis = new FileInputStream(input);
            ZipEntry ze = new ZipEntry(input.getName());
            System.out.println("Zipping the file: "+input.getName());
            zipOut.putNextEntry(ze);
            byte[] tmp = new byte[4*1024];
            int size = 0;
            while((size = fis.read(tmp)) != -1){
                zipOut.write(tmp, 0, size);
            }
            zipOut.flush();
            zipOut.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try{
                if(fos != null) fos.close();
                if(fis != null) fis.close();
            } catch(Exception ex){

            }
        }
    }

    public BulkIndicatorResponse invokeBatchInterface(BulkIndicatorRequest request) {
        String serviceUrl = microServicesRepository.getMICROSERVICES_LOCATIONSByInterfaceId(request.getMetaData().getInterfaceId()).getServiceUrl();
        //request.getMetaData().setInterfaceId("LoadSundryCallBack");
        BulkIndicatorResponse retRes = (BulkIndicatorResponse) camelContext.createProducerTemplate().sendBodyAndHeader(
                "direct:loadsundry", ExchangePattern.InOut, request, "serviceUrl", serviceUrl
                );

        BulkIndicatorResponse response = new BulkIndicatorResponse();

        response.setResult(new ResultType());
        response.getResult().setErrorList(new ErrorsType());

        response.getResult().setStatus(retRes.getResult().getStatus());
        response.getResult().setIsError(retRes.getResult().isIsError());
        response.setMessageId(retRes.getMessageId());
        response.setSenderTimestamp(retRes.getSenderTimestamp());

        return response;
    }



}
