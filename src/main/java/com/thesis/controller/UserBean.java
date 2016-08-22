package com.thesis.controller;

import com.thesis.model.Account;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by Alex on 21-Aug-16.
 */
@SessionScoped
@Named("userBean")
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Account user;

    public boolean isAdmin() {
        return user.isAdmin();
    }

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
}
