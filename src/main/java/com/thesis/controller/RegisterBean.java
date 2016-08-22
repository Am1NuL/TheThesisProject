package com.thesis.controller;

import com.thesis.crud.PersistException;
import com.thesis.facade.AccountFacade;
import com.thesis.model.Account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.NoResultException;

/**
 * Created by Alex on 21-Aug-16.
 */
@RequestScoped
@Named
public class RegisterBean extends AbstractBean{
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Account user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String register() throws PersistException {
        AccountFacade accountFacade = new AccountFacade();

        try {
            user = accountFacade.isInfoUnique(username, email);
        }catch (NoResultException e) {
        }

        if(user == null && password.equals(confirmPassword)) {
            accountFacade.createAccount(username, password, email);
            displayInfoMessageToUser("Registration is successful");
            return "/pages/public/login.xhtml";
        }

        displayErrorMessageToUser("Registration error");

        return null;
    }
}
