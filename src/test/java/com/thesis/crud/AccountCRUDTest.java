package com.thesis.crud;

import com.thesis.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alex on 22-Aug-16.
 */
public class AccountCRUDTest {
    private Account user;
    private Account testUser;

    @Before
    public void setUp() {
        user = new Account("alexander", "aleksander931", "wboy@abv.bg");
    }

    @Test
    public void createPerson() throws PersistException {

        DAO dao = new DAO();
        dao.getAccountCRUD().create(user);
        dao.commit();

        dao = new DAO();
        testUser = dao.getAccountCRUD().read(user.getAccId());
        dao.commit();

        Assert.assertEquals(user, testUser);
    }
}
