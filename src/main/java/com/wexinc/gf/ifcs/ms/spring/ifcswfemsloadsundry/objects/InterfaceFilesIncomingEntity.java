package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.objects;

import javax.persistence.*;

@Entity
@Table(name = "INTERFACE_FILES_INCOMING", schema = "IFCS_WFE_DEV_2", catalog = "")
public class InterfaceFilesIncomingEntity {
    private boolean interfaceFileIncomingOid;
    private String interfaceFileIncomingId;
    private Long clientMid;
    private Long lastSequenceNumber;
    private String inputFileNamePrefix;
    private Boolean maximumRecords;
    private String inputFileFolderName;
    private String sequenceNumberTemplate;

    @Id
    @Column(name = "INTERFACE_FILE_INCOMING_OID")
    public boolean isInterfaceFileIncomingOid() {
        return interfaceFileIncomingOid;
    }

    public void setInterfaceFileIncomingOid(boolean interfaceFileIncomingOid) {
        this.interfaceFileIncomingOid = interfaceFileIncomingOid;
    }

    @Basic
    @Column(name = "INTERFACE_FILE_INCOMING_ID")
    public String getInterfaceFileIncomingId() {
        return interfaceFileIncomingId;
    }

    public void setInterfaceFileIncomingId(String interfaceFileIncomingId) {
        this.interfaceFileIncomingId = interfaceFileIncomingId;
    }

    @Basic
    @Column(name = "CLIENT_MID")
    public Long getClientMid() {
        return clientMid;
    }

    public void setClientMid(Long clientMid) {
        this.clientMid = clientMid;
    }

    @Basic
    @Column(name = "LAST_SEQUENCE_NUMBER")
    public Long getLastSequenceNumber() {
        return lastSequenceNumber;
    }

    public void setLastSequenceNumber(Long lastSequenceNumber) {
        this.lastSequenceNumber = lastSequenceNumber;
    }

    @Basic
    @Column(name = "INPUT_FILE_NAME_PREFIX")
    public String getInputFileNamePrefix() {
        return inputFileNamePrefix;
    }

    public void setInputFileNamePrefix(String inputFileNamePrefix) {
        this.inputFileNamePrefix = inputFileNamePrefix;
    }

    @Basic
    @Column(name = "MAXIMUM_RECORDS")
    public Boolean getMaximumRecords() {
        return maximumRecords;
    }

    public void setMaximumRecords(Boolean maximumRecords) {
        this.maximumRecords = maximumRecords;
    }

    @Basic
    @Column(name = "INPUT_FILE_FOLDER_NAME")
    public String getInputFileFolderName() {
        return inputFileFolderName;
    }

    public void setInputFileFolderName(String inputFileFolderName) {
        this.inputFileFolderName = inputFileFolderName;
    }

    @Basic
    @Column(name = "SEQUENCE_NUMBER_TEMPLATE")
    public String getSequenceNumberTemplate() {
        return sequenceNumberTemplate;
    }

    public void setSequenceNumberTemplate(String sequenceNumberTemplate) {
        this.sequenceNumberTemplate = sequenceNumberTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterfaceFilesIncomingEntity that = (InterfaceFilesIncomingEntity) o;

        if (interfaceFileIncomingOid != that.interfaceFileIncomingOid) return false;
        if (clientMid != that.clientMid) return false;
        if (lastSequenceNumber != that.lastSequenceNumber) return false;
        if (interfaceFileIncomingId != null ? !interfaceFileIncomingId.equals(that.interfaceFileIncomingId) : that.interfaceFileIncomingId != null)
            return false;
        if (inputFileNamePrefix != null ? !inputFileNamePrefix.equals(that.inputFileNamePrefix) : that.inputFileNamePrefix != null)
            return false;
        if (maximumRecords != null ? !maximumRecords.equals(that.maximumRecords) : that.maximumRecords != null)
            return false;
        if (inputFileFolderName != null ? !inputFileFolderName.equals(that.inputFileFolderName) : that.inputFileFolderName != null)
            return false;
        if (sequenceNumberTemplate != null ? !sequenceNumberTemplate.equals(that.sequenceNumberTemplate) : that.sequenceNumberTemplate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (interfaceFileIncomingOid ? 1 : 0);
        result = 31 * result + (interfaceFileIncomingId != null ? interfaceFileIncomingId.hashCode() : 0);
        result = 31 * result + (clientMid != null ? clientMid.hashCode():0);
        result = 31 * result + (lastSequenceNumber != null ? lastSequenceNumber.hashCode():0);
        result = 31 * result + (inputFileNamePrefix != null ? inputFileNamePrefix.hashCode() : 0);
        result = 31 * result + (maximumRecords != null ? maximumRecords.hashCode() : 0);
        result = 31 * result + (inputFileFolderName != null ? inputFileFolderName.hashCode() : 0);
        result = 31 * result + (sequenceNumberTemplate != null ? sequenceNumberTemplate.hashCode() : 0);
        return result;
    }
}
