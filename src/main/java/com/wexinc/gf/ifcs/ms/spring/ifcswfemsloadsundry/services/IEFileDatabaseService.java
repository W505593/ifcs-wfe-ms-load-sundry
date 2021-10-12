package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.services;

import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects.CallbackExecutions;
import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories.ie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class IEFileDatabaseService {
    private final CallbackExecRepository callbackRepository;

    @Autowired
    public IEFileDatabaseService(CallbackExecRepository callbackRepository) {
        this.callbackRepository = callbackRepository;
    }

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String MODIFYING = "MODIFYING";

    public static final String COMPLETED = "COMPLETED";
    public static final String WAITING_CALLBACK = "WAITING_CALLBACK";


    public static final String FILE_TRANSFORMATION_FAILED = "File Transformation Failed";
    public static final String INTERNAL_FILE_EXCEPTION = "Failed to create file";
    public static final String FILE_ALREADY_EXISTS = "File Already Exists";
    public static final String EXTERNAL_FILE_PARSE_ERROR = "Error reading External File";


    public void createCallbackRecord(String interfaceId, String clientMid, String serviceUrl, String correlationId) {
        CallbackExecutions record = new CallbackExecutions();

        Optional<CallbackExecutions> existing = callbackRepository.getByInterfaceIdAndCorrelationId(interfaceId, correlationId);

        if (existing.isPresent() && existing.get().getStatus().equals(WAITING_CALLBACK)) {
            existing.get().setClientId(clientMid);
            existing.get().setServiceUrl(serviceUrl);
            existing.get().setStatus(WAITING_CALLBACK);
            existing.get().setCorrelationId(correlationId);
            existing.get().setInterfaceId(interfaceId);
            callbackRepository.save(existing.get());
        } else {
            record.setClientId(clientMid);
            record.setServiceUrl(serviceUrl);
            record.setStatus(WAITING_CALLBACK);
            record.setCorrelationId(correlationId);
            record.setInterfaceId(interfaceId);
            callbackRepository.save(record);
        }
    }

    public void successfulCallback(String interfaceId, String corrolaryId) {
        Optional<CallbackExecutions> existing = callbackRepository.getByInterfaceIdAndCorrelationId(interfaceId, corrolaryId);

        if(existing.get().getStatus().equals(WAITING_CALLBACK)) {
            existing.get().setStatus(COMPLETED);
        }

        callbackRepository.save(existing.get());
    }

}
