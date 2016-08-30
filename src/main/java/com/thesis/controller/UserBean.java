package com.thesis.controller;

import com.thesis.facade.AccountFacade;
import com.thesis.model.Account;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Alex on 21-Aug-16.
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Account user;
    private List<Account> users;
    private AccountFacade accountFacade;
    private String selectedUser;

    //TODO: accessDenied
    public boolean isAdmin() { return user.isAdmin(); }

    public boolean isDefaultUser() {
        return user.isUser();
    }

    public String logOut() {
        getRequest().getSession().invalidate();
        return "/pages/public/login.xhtml";
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public AccountFacade getAccountFacade() {
        if(accountFacade == null) {
            accountFacade = new AccountFacade();
        }

        return accountFacade;
    }

    public List<Account> getAllAccounts() {
        if(users == null) {
            loadUsers();
        }

        return users;
    }

    public void loadUsers() { users =  getAccountFacade().listAll(); }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }
}
