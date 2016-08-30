package com.thesis.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
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

    @ManyToMany(mappedBy = "fileList", cascade = CascadeType.ALL)
    private Set<Account> accountList = new HashSet<Account>();

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

    public void addUser(Account toAdd) {
        this.accountList.add(toAdd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        return !(fileId != null ? !fileId.equals(file.fileId) : file.fileId != null);

    }

    @Override
    public int hashCode() {
        return fileId != null ? fileId.hashCode() : 0;
    }
}
