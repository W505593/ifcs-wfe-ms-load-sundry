package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class M_Clients {
    @Id
    private String client_mid;
    private String short_name;
    private String country_Oid;

    public M_Clients(String client_mid, String short_name, String country_Oid) {
        this.client_mid = client_mid;
        this.short_name = short_name;
        this.country_Oid = country_Oid;
    }

    public M_Clients() {

    }

    public String getClient_mid() {
        return client_mid;
    }

    public void setClient_mid(String client_mid) {
        this.client_mid = client_mid;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getCountry_Oid() {
        return country_Oid;
    }

    public void setCountry_Oid(String country_Oid) {
        this.country_Oid = country_Oid;
    }
}
