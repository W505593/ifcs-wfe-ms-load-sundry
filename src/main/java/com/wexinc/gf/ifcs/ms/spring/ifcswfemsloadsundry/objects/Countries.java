package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Countries {
    @Id
    String country_Oid;
    String country_Code;

    public Countries(String country_Oid, String country_Code) {
        this.country_Oid = country_Oid;
        this.country_Code = country_Code;
    }

    public Countries() {

    }

    public String getCountry_Oid() {
        return country_Oid;
    }

    public void setCountry_Oid(String country_Oid) {
        this.country_Oid = country_Oid;
    }

    public String getCountry_Code() {
        return country_Code;
    }

    public void setCountry_Code(String country_Code) {
        this.country_Code = country_Code;
    }
}
