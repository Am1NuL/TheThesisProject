package com.thesis.crud;

import com.thesis.entities.Account;
import com.thesis.util.PasswordEncryption;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Alex on 22-Aug-16.
 */
public class AccountCRUDTest {
    private DAO dao;
    private Account user;
    private String username;
    private String password;
    private String mail;
    private String testMail;
    private Account testUser;
    private PasswordEncryption passwordEncryption;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
        dao = new DAO();
        passwordEncryption = new PasswordEncryption();
        user = new Account();
        username = "potrebitel";
        password = "password1";
        mail = "test@test.com";
        testMail = "ddas@test.com";
        byte[] salt = passwordEncryption.generateSalt();
        byte[] cryptPass = passwordEncryption.getEncryptedPassword(password, salt);
        user.setUsername(username);
        user.setPassword(cryptPass);
        user.setEmail(mail);
        user.setSalt(salt);
    }

    @Test
    public void createAccount() throws PersistException {

        dao.getAccountCRUD().create(user);
        dao.commit();

        final DAO readDAO = new DAO();
        testUser = readDAO.getAccountCRUD().read(user.getAccId());
        Assert.assertEquals(user, testUser);
        readDAO.commit();
    }

    @Test
    public void createNullUser() throws PersistException {

        thrown.expect(IllegalArgumentException.class);
        dao.getAccountCRUD().create(null);
        dao.commit();
    }

    @Test
    public void updateAccountEmail() throws PersistException {

        dao.getAccountCRUD().create(user);
        dao.commit();

        final DAO updateDAO = new DAO();
        testUser = updateDAO.getAccountCRUD().read(user.getAccId());
        testUser.setEmail(testMail);
        updateDAO.commit();

        final DAO readDAO = new DAO();
        testUser = readDAO.getAccountCRUD().read(user.getAccId());

        Assert.assertEquals(testMail, testUser.getEmail());
        readDAO.commit();
    }

    @Test
    public void deleteUser() throws PersistException {

        dao.getAccountCRUD().create(user);
        dao.commit();

        final DAO deleteDAO = new DAO();
        testUser = deleteDAO.getAccountCRUD().read(user.getAccId());
        deleteDAO.getAccountCRUD().delete(testUser);
        deleteDAO.commit();

        final DAO readDAO = new DAO();
        testUser = readDAO.getAccountCRUD().read(user.getAccId());

        Assert.assertEquals(testUser, null);
        readDAO.commit();
    }

    @Test
    public void deleteNullUser() throws PersistException {

        thrown.expect(IllegalArgumentException.class);
        dao.getAccountCRUD().delete(null);
        dao.commit();
    }
}
