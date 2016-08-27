package com.thesis.facade;

import com.thesis.crud.DAO;
import com.thesis.crud.PersistException;
import com.thesis.model.Account;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by Alex on 21-Aug-16.
 */
public class AccountFacade {
    public Account isValidLogin(String username, String password) throws NoResultException{
        DAO dao = new DAO();
        Account user = dao.getAccountCRUD().findUserByUsername(username);

        if(user == null || !user.getPassword().equals(password)) {
            return null;
        }

        dao.closeTransaction();
        return user;
    }

    public Account isInfoUnique(String username, String email) throws NoResultException{
        DAO dao = new DAO();
        Account emailCheck = dao.getAccountCRUD().findUserByEmail(email);
        Account usernameCheck = dao.getAccountCRUD().findUserByUsername(username);

        dao.closeTransaction();
        return null;

        /*if(usernameCheck != null) {
            return usernameCheck;
        } else {
            return emailCheck;
        }*/
    }

    public void createAccount(String username, String password, String email) throws PersistException {
        DAO dao = new DAO();
        Account account = null;
        if(username != null && password != null && email != null) {
            account = new Account(username, password, email);
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

    /*public void updatePassword(User user, String oldPassword, String newPassword) {
        userDAO.beginTransaction();
        User persistedUser = userDAO.find(user.getId());
        if (user != null && user.getPassword().equals(oldPassword)) {
            persistedUser.setPassword(newPassword);
            userDAO.update(persistedUser);
            userDAO.commitAndCloseTransaction();
        }
    }*/
}
