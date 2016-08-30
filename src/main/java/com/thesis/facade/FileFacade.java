package com.thesis.facade;

import com.thesis.controller.AbstractBean;
import com.thesis.crud.DAO;
import com.thesis.crud.PersistException;
import com.thesis.model.Account;
import com.thesis.model.File;
import com.thesis.model.Shared;
import com.thesis.util.JSFMessageUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Alex on 25-Aug-16.
 */
public class FileFacade extends JSFMessageUtil{
    public void upload(File file) throws PersistException {
        //TODO: check for file name, if duplicate - rename
        DAO dao = new DAO();
        Account user = AccountFacade.getCurrentUser();
        dao.getFileCRUD().create(file);
        user.addFile(file);
        dao.commit();
        dao = new DAO();
        Shared shared = new Shared();
        shared.setAccountId(user.getAccId());
        shared.setFileId(file.getFileId());
        dao.getSharedCRUD().create(shared);
        dao.commit();
        System.out.println("File List: " + user.getFileList().size());
    }

    public void share(String userId, UUID fileId) throws PersistException {
        final DAO dao = new DAO();
        UUID uuid1 = UUID.fromString(userId);
        Account user = dao.getAccountCRUD().read(uuid1);
        dao.closeTransaction();
        final DAO dao2 = new DAO();
        File file = (File) dao2.getFileCRUD().read(fileId);
        dao2.closeTransaction();
//        user.addFile(file);
        final DAO dao3 = new DAO();
        Shared shared = new Shared();
        shared.setAccountId(user.getAccId());
        shared.setFileId(file.getFileId());
        dao3.closeTransaction();
        if(user.getFileList().contains(file)) {
            System.out.println("HAHA ni moa suzdam takoz veche ima!");
            sendErrorMessageToUser("File is already Shared!");
        }
        else{
            final DAO dao4 = new DAO();
            dao4.getSharedCRUD().create(shared);
            dao4.commit();
            sendInfoMessageToUser("File is Shared");
        }
    }

    public File getFile(UUID fileId) throws NoResultException{
        DAO dao = new DAO();
        File file = dao.getFileCRUD().findFileById(fileId);
        dao.closeTransaction();
        return file;
    }

    public Set<File> listAllFilesByUser() {
        DAO dao = new DAO();
        Set<File> files = AccountFacade.getCurrentUser().getFileList();
        dao.closeTransaction();
        return files;
    }

    public List<File> listAll() {
        DAO dao = new DAO();
        List<File> result = dao.getFileCRUD().findAll();
        dao.closeTransaction();
        return result;
    }
}
