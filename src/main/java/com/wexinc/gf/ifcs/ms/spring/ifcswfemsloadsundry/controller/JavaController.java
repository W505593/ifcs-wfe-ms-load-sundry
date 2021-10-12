package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.controller;

import com.wex.schema.callbackresponse._1.InterfaceProcessingResultType;
import com.wex.schema.callbackresponse._1.ReturnFlag;
import com.wexinc.gf.ifcs.ms.spring.*;
import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.services.IEFileDatabaseService;
import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.services.SundryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("sundry-adjustment")
public class JavaController {
    private static final Logger logger = LoggerFactory.getLogger(SundryService.class);
    private final SundryService sundryService;

    private final IEFileDatabaseService ieService;

    @Autowired
    public JavaController(SundryService sundryService, IEFileDatabaseService ieService){
        this.sundryService = sundryService;
        this.ieService = ieService;
    }

    private final static String INTERFACE_ID = "LoadSundryAdjustmentTransactions";

    @PostMapping("readCsv")
    void readCsv(@RequestBody BulkIndicatorRequest bulkIndicatorRequest){
        String csvFileName = bulkIndicatorRequest.getPayloadDetails().getFileName();

        if(bulkIndicatorRequest.getPayloadDetails().getDirectory() != null && csvFileName!=null) {
            Path destination = Paths.get(bulkIndicatorRequest.getPayloadDetails().getDirectory()).resolve(csvFileName);

            ResultType resultType = sundryService.readCsvFile(destination, bulkIndicatorRequest);
            if(!resultType.isIsError()){
                System.out.println(bulkIndicatorRequest.getPayloadDetails().getFileName() + " ");
                BulkIndicatorResponse bulkIndicatorResponse = sundryService.invokeBatchInterface(bulkIndicatorRequest);
                logger.info(bulkIndicatorResponse.toString());
            }else{
                logger.error("An error is in readCsvFile function");
            }


        }else{
            logger.error("There is not a directory or filename present in the request");
        }
    }

    @PostMapping("/callback")
    public ReturnFlag callback(@RequestBody InterfaceProcessingResultType request){
        ReturnFlag response = new ReturnFlag();

        if (request.getFault().getMessage() != null) {
            logger.error(request.getFault().getMessage());

            response.setIsErrorCode(true);
            response.setMessage("Callback for error received, Terminating request session");
            response.setStatusNumber(Long.parseLong(request.getFault().getCode()));
            return response;
        } else {
            ieService.successfulCallback(request.getInterfaceID(), request.getCorrelationID());
        }

        /*BulkIndicatorRequest interfaceRequest = new BulkIndicatorRequest();
        interfaceRequest.setMetaData(new MetaDataType());
        interfaceRequest.setPayloadDetails(new PayloadDetailsType());

        interfaceRequest.getMetaData().setInterfaceId(INTERFACE_ID);
        interfaceRequest.getMetaData().setTargetSystem(request.getClientMid());

        BulkIndicatorResponse interfaceResponse = sundryService.invokeBatchInterface(interfaceRequest);*/
        response.setIsErrorCode(false);
        return response;
    }
}
