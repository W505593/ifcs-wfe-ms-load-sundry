package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.repositories.ifcs;

import com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects.M_Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MClientRepository extends JpaRepository<M_Clients, Integer> {
    @Query(value = "SELECT m.client_mid FROM M_Clients m LEFT JOIN Countries c ON m.country_Oid = c.country_Oid WHERE c.country_Code = ?1")
    public Long findClientMid(String countryCode);
}
