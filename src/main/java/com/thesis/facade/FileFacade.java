package com.thesis.facade;

import com.thesis.crud.DAO;
import com.thesis.crud.PersistException;
import com.thesis.model.File;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alex on 25-Aug-16.
 */
public class FileFacade {
    public void upload(File file) throws PersistException {
        DAO dao = new DAO();
        dao.getFileCRUD().create(file);
        dao.commit();
    }

    public File getFile(UUID fileId) throws NoResultException{
        DAO dao = new DAO();
        File file = dao.getFileCRUD().findFileById(fileId);
        dao.closeTransaction();
        return file;
    }

    public List<File> listAll() {
        DAO dao = new DAO();
        List<File> result = dao.getFileCRUD().findAll();
        dao.closeTransaction();
        return result;
    }
}
