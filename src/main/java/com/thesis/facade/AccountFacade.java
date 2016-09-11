package com.thesis.facade;

import com.thesis.crud.DAO;
import com.thesis.crud.PersistException;
import com.thesis.entities.Account;
import com.thesis.util.PasswordEncryption;

import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by Alex on 21-Aug-16.
 */
public class AccountFacade {
    public Account isValidLogin(String username, String password) throws NoResultException {
        DAO dao = new DAO();
        Account user = dao.getAccountCRUD().findUserByUsername(username);
        dao.closeTransaction();

        PasswordEncryption checkPass = new PasswordEncryption();

        try {
            if(user == null || !checkPass.authenticate(password, user.getPassword(), user.getSalt() )) {
                return null;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Account isInfoUnique(String username, String email) throws NoResultException{
        DAO dao = new DAO();
        Account emailCheck = dao.getAccountCRUD().findUserByEmail(email);
        dao.closeTransaction();

        dao = new DAO();
        Account usernameCheck = dao.getAccountCRUD().findUserByUsername(username);
        dao.closeTransaction();
        return null;
    }

    public void createAccount(String username, String password, String email) throws PersistException, InvalidKeySpecException, NoSuchAlgorithmException {
        DAO dao = new DAO();
        Account account = null;
        PasswordEncryption passwordEncryption = new PasswordEncryption();
        byte[] salt;
        byte[] encryptPassword;
        if(username != null && password != null && email != null) {
            salt = passwordEncryption.generateSalt();
            encryptPassword = passwordEncryption.getEncryptedPassword(password, salt);
            account = new Account(username, encryptPassword, email, salt);
            dao.getAccountCRUD().create(account);
            dao.commit();
        }
    }

    public List<Account> listAll() {
        DAO dao = new DAO();
        List<Account> result = dao.getAccountCRUD().findAll();
        dao.closeTransaction();
        return result;
    }

    public static Account getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return (Account) request.getSession().getAttribute("user");
    }

    public void updatePassword(String oldPassword, String newPassword) throws InvalidKeySpecException, NoSuchAlgorithmException, PersistException {
        DAO dao = new DAO();
        PasswordEncryption passwordEncryption = new PasswordEncryption();
        Account user = getCurrentUser();
        if (user != null && passwordEncryption.authenticate(oldPassword, user.getPassword(), user.getSalt())) {
            user.setPassword(passwordEncryption.getEncryptedPassword(newPassword, user.getSalt()));
            dao.getAccountCRUD().update(user);
            dao.commit();
        }
    }

    public void updateMail(String password, String mail) throws InvalidKeySpecException, NoSuchAlgorithmException, PersistException {
        DAO dao = new DAO();
        PasswordEncryption passwordEncryption = new PasswordEncryption();
        Account user = getCurrentUser();
        if(user != null && passwordEncryption.authenticate(password, user.getPassword(), user.getSalt())) {
            user.setEmail(mail);
            dao.getAccountCRUD().update(user);
            dao.commit();
        }
    }

    public void updateAccount(Account user) throws PersistException {
        DAO dao = new DAO();
        Account acc = dao.getAccountCRUD().read(user.getAccId());
        if(user.getEmail().equals("")) {
            acc.setRole(user.getRole());
        }else {
            acc.setEmail(user.getEmail());
            acc.setRole(user.getRole());
        }
        dao.getAccountCRUD().update(acc);
        dao.commit();
    }

    public void deleteAccount(Account user) throws PersistException {
        DAO dao = new DAO();
        Account acc = dao.getAccountCRUD().read(user.getAccId());
        dao.getAccountCRUD().delete(acc);
        dao.commit();
    }
}
