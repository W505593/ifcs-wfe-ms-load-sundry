package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories.ie;

import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects.CallbackExecutions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CallbackExecRepository extends CrudRepository<CallbackExecutions, Long> {

    Optional<CallbackExecutions> getByInterfaceIdAndCorrelationId(String interfaceId, String correlationId);
}
