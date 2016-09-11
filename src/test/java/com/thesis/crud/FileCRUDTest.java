package com.thesis.crud;

import com.thesis.entities.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

/**
 * Created by Alex on 05-Sep-16.
 */
public class FileCRUDTest {
    private DAO dao;
    private File file;
    private File testFile;
    private String name;
    private byte[] data;
    private byte[] testData;
    private String owner;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        dao = new DAO();
        file = new File();
        name = "FileName";
        data = new byte[20];
        testData = new byte[20];
        new Random().nextBytes(data);
        new Random().nextBytes(testData);
        owner = "user";
        file.setFileName(name);
        file.setData(data);
        file.setOwner(owner);
    }

    @Test
    public void createFile() throws PersistException {

        dao.getFileCRUD().create(file);
        dao.commit();

        final DAO readDAO = new DAO();
        testFile = (File) readDAO.getFileCRUD().read(file.getFileId());
        Assert.assertEquals(file, testFile);
        readDAO.commit();
    }

    @Test
    public void createNullFile() throws PersistException {
        thrown.expect(IllegalArgumentException.class);
        dao.getFileCRUD().create(null);
        dao.commit();
    }

    @Test
    public void deleteFile() throws PersistException {

        dao.getFileCRUD().create(file);
        dao.commit();

        final DAO deleteDAO = new DAO();
        testFile = (File) deleteDAO.getFileCRUD().read(file.getFileId());
        deleteDAO.getFileCRUD().delete(testFile);
        deleteDAO.commit();

        final DAO readDAO = new DAO();
        testFile = (File) readDAO.getFileCRUD().read(file.getFileId());

        Assert.assertEquals(testFile, null);
        readDAO.commit();
    }

    @Test
    public void deleteNullFile() throws PersistException {

        thrown.expect(IllegalArgumentException.class);
        dao.getFileCRUD().delete(null);
        dao.commit();
    }
}
