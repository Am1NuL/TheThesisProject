package com.thesis.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Alex on 28-Aug-16.
 */
@Entity
@Table(name = "account_file")
public class Shared implements Serializable{

    @Id
    @Column(name = "ACCOUNT_ID")
    private UUID accountId;

    @Id
    @Column(name = "FILE_ID")
    private UUID fileId;

    public Shared() {
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shared shared = (Shared) o;

        if (accountId != null ? !accountId.equals(shared.accountId) : shared.accountId != null) return false;
        return !(fileId != null ? !fileId.equals(shared.fileId) : shared.fileId != null);

    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        return result;
    }
}
