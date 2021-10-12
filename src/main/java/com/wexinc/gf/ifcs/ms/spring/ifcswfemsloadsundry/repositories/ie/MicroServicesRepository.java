package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories.ie;

import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects.MICROSERVICES_LOCATIONS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroServicesRepository extends JpaRepository<MICROSERVICES_LOCATIONS, Integer> {
    public MICROSERVICES_LOCATIONS getMICROSERVICES_LOCATIONSByInterfaceId (String interfaceId);
}
