package com.thesis.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Alex on 26-Jun-16.
 */
@Entity
@Table(name = "accounts")
public class Account {

    private static final int MAXIMUM_PASSWORD_LENGTH = 16;

    @Id
    @Column(name = "ACCOUNT_ID")
    private UUID accId = UUID.randomUUID();

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD", length = MAXIMUM_PASSWORD_LENGTH)
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "account_file", joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "FILE_ID"))
    private Set<File> fileList = new HashSet<File>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public Account(){}

    public Account(String user, String pass, String mail) {
        this.setUsername(user);
        this.setPassword(pass);
        this.setEmail(mail);
        this.setRole(Role.USER);
    }

    public UUID getAccId() {
        return accId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        this.email = email;
    }

    public Set<File> getFileList() {
        return Collections.unmodifiableSet(this.fileList);
    }

    public void addFile(File toAdd) {
        this.fileList.add(toAdd);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return Role.ADMIN.equals(role);
    }

    public boolean isUser() {
        return Role.USER.equals(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return !(accId != null ? !accId.equals(account.accId) : account.accId != null);
    }

    @Override
    public int hashCode() {
        return accId != null ? accId.hashCode() : 0;
    }
}
