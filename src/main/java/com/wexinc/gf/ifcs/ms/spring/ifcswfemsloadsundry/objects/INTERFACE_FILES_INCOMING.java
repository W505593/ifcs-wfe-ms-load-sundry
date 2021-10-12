package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class INTERFACE_FILES_INCOMING {
    @Id
    String Interface_File_Incoming_Oid;
    String last_Sequence_Number;
    String Interface_File_Incoming_Id;

    public INTERFACE_FILES_INCOMING(String interface_File_Incoming_Oid, String last_Sequence_Number, String interface_File_Incoming_Id) {
        Interface_File_Incoming_Oid = interface_File_Incoming_Oid;
        this.last_Sequence_Number = last_Sequence_Number;
        Interface_File_Incoming_Id = interface_File_Incoming_Id;
    }

    public INTERFACE_FILES_INCOMING() {

    }

    public String getInterface_File_Incoming_Oid() {
        return Interface_File_Incoming_Oid;
    }

    public void setInterface_File_Incoming_Oid(String interface_File_Incoming_Oid) {
        Interface_File_Incoming_Oid = interface_File_Incoming_Oid;
    }

    public String getLast_Sequence_Number() {
        return last_Sequence_Number;
    }

    public void setLast_Sequence_Number(String last_Sequence_Number) {
        this.last_Sequence_Number = last_Sequence_Number;
    }

    public String getInterface_File_Incoming_Id() {
        return Interface_File_Incoming_Id;
    }

    public void setInterface_File_Incoming_Id(String interface_File_Incoming_Id) {
        Interface_File_Incoming_Id = interface_File_Incoming_Id;
    }
}
