package com.thesis.facade;

import com.thesis.crud.DAO;
import com.thesis.crud.PersistException;
import com.thesis.model.File;

/**
 * Created by Alex on 25-Aug-16.
 */
public class FileFacade {
    public void upload(File file) throws PersistException {
        DAO dao = new DAO();
        dao.getFileCRUD().create(file);
        dao.commit();
    }
}
