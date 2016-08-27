package com.thesis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Alex on 25-Aug-16.
 */
@Entity
@Table(name="files")
public class File {

    @Id
    @Column(name = "FILE_ID")
    private UUID fileId = UUID.randomUUID();

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_DATA")
    private byte[] data;

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
