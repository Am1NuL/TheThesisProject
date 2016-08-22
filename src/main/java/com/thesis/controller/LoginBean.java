package com.thesis.controller;

import com.thesis.facade.AccountFacade;
import com.thesis.model.Account;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Alex on 21-Aug-16.
 */
@RequestScoped
@Named
public class LoginBean extends AbstractBean{
    @Inject
    private UserBean userBean;

    private String username;
    private String password;

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

    public String login() {
        AccountFacade accountFacade = new AccountFacade();

        try {
            user = accountFacade.isValidLogin(username, password);
        }catch (NoResultException e) {
        }


        if(user != null) {
            userBean.setUser(user);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute("user", user);
            return "/pages/protected/index.xhtml";
        }

        displayErrorMessageToUser("Check your username/password");

        return null;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
