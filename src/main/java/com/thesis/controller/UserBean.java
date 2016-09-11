package com.thesis.controller;

import com.thesis.crud.PersistException;
import com.thesis.facade.AccountFacade;
import com.thesis.entities.Account;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by Alex on 21-Aug-16.
 */
@Named("userBean")
@SessionScoped
public class UserBean extends AbstractBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Account user;
    private Account dummyUser;
    private List<Account> users;
    private AccountFacade accountFacade;
    private String selectedUser;
    private String oldPass;
    private String oldPassConf;
    private String newPass;
    private String newMail;
    private String newMailConf;

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

    public void updateUser() {
        try {
            getAccountFacade().updateAccount(dummyUser);
//            closeDialog();
            displayInfoMessageToUser("Update is successful");
            loadUsers();
            resetUser();
        } catch (Exception e) {
            loadUsers();
            displayErrorMessageToUser("Ops, we could not update. Try again later");
            e.printStackTrace();
        }
    }

    public void createUser() {
        try {
            getAccountFacade().createAccount(dummyUser.getUsername(), dummyUser.getPassword().toString(), dummyUser.getEmail());
            displayInfoMessageToUser("Creation is successful");
            loadUsers();
            resetUser();
        } catch (Exception e) {
            loadUsers();
            displayErrorMessageToUser("Ops, we could not create. Try again later");
            e.printStackTrace();
        }
    }

    public void deleteUser(Account user) {
        dummyUser = user;
        try {
            getAccountFacade().deleteAccount(dummyUser);
            displayInfoMessageToUser("Deletion is successful");
            loadUsers();
            resetUser();
        } catch (PersistException e) {
            loadUsers();
            displayErrorMessageToUser("Ops, we could not delete. Try again later");
            e.printStackTrace();
        }
    }

    public void changePassword() throws InvalidKeySpecException, NoSuchAlgorithmException, PersistException {
        if(oldPass.equals(oldPassConf)) {
            getAccountFacade().updatePassword(oldPass, newPass);
            displayInfoMessageToUser("Password is changed successfully");
        }else {
            displayErrorMessageToUser("Invalid password input");
        }
    }

    public void changeMail() throws InvalidKeySpecException, NoSuchAlgorithmException, PersistException {
        if(newMail.equals(newMailConf)) {
            getAccountFacade().updateMail(oldPass, newMail);
            displayInfoMessageToUser("Mail is changed successfully");
        } else {
            displayErrorMessageToUser("Invalid input");
        }
    }

    public void loadUsers() { users =  getAccountFacade().listAll(); }

    public void resetUser() {
        dummyUser = new Account();
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Account getDummyUser() {
        return dummyUser;
    }

    public void setDummyUser(Account dummyUser) {
        this.dummyUser = dummyUser;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getOldPassConf() {
        return oldPassConf;
    }

    public void setOldPassConf(String oldPassConf) {
        this.oldPassConf = oldPassConf;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewMail() {
        return newMail;
    }

    public void setNewMail(String newMail) {
        this.newMail = newMail;
    }

    public String getNewMailConf() {
        return newMailConf;
    }

    public void setNewMailConf(String newMailConf) {
        this.newMailConf = newMailConf;
    }
}
