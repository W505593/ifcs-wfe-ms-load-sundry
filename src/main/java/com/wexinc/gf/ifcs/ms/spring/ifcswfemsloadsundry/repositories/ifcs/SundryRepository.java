package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories.ifcs;

import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects.InterfaceFilesIncomingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SundryRepository extends JpaRepository<InterfaceFilesIncomingEntity, Integer> {
    @Query(value = "SELECT i FROM InterfaceFilesIncomingEntity i WHERE i.interfaceFileIncomingId = 'LoadSundryAdjustmentTransactions' and i.clientMid=:clientMid")
    public InterfaceFilesIncomingEntity interfaceIncomingRows(@Param("clientMid") Long clientMid);


}
