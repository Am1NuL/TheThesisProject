package com.thesis.entities;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Column(name = "DATE_CREATED")
    private String date;

    @Column(name = "OWNER")
    private String owner;

    @ManyToMany(mappedBy = "fileList", cascade = CascadeType.ALL)
    private Set<Account> accountList = new HashSet<Account>();

    public File() {
        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        Date tempDate = new Date();
        this.setDate(dateFormat.format(tempDate));
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFileSize() {
        int kilobyte = data.length / 1024;
        int megabytes = kilobyte / 1024;
        String size;

        if(data.length >= 1048576) {
            size = megabytes + " MB";
        }else {
            size = kilobyte +  " KB";
        }

        return size;
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
