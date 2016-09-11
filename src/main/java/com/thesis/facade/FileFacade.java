package com.thesis.facade;

import com.thesis.crud.DAO;
import com.thesis.crud.PersistException;
import com.thesis.entities.Account;
import com.thesis.entities.File;
import com.thesis.entities.Shared;
import com.thesis.util.JSFMessageUtil;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Alex on 25-Aug-16.
 */
public class FileFacade extends JSFMessageUtil{
    public void upload(File file) throws PersistException {
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

        /*DAO dao = new DAO();
        Account user = AccountFacade.getCurrentUser();
        dao.getFileCRUD().create(file);
        user.addFile(file);
        dao.getAccountCRUD().update(user);
        dao.commit();
        System.out.println("File List: " + user.getFileList().size());*/
    }

    public void share(String userId, UUID fileId) throws PersistException {
        DAO dao = new DAO();
        UUID uuid1 = UUID.fromString(userId);
        Account user = dao.getAccountCRUD().read(uuid1);
        dao.closeTransaction();
        dao = new DAO();
        File file = (File) dao.getFileCRUD().read(fileId);
        dao.closeTransaction();
        dao = new DAO();
        Shared shared = new Shared();
        shared.setAccountId(user.getAccId());
        shared.setFileId(file.getFileId());
        dao.closeTransaction();
        if(user.getFileList().contains(file)) {
            System.out.println("Duplicate entry, already shared!");
            sendErrorMessageToUser("File is already Shared!");
        }
        else{
            dao = new DAO();
//            user.addFile(file);
            dao.getSharedCRUD().create(shared);
//            dao.getAccountCRUD().update(user);
            dao.commit();
            sendInfoMessageToUser("File is Shared");
        }
    }

    public File getFile(UUID fileId) throws NoResultException{
        DAO dao = new DAO();
        File file = dao.getFileCRUD().findFileById(fileId);
        dao.closeTransaction();
        return file;
    }

    public void removeFile(UUID fileId){
        DAO dao = new DAO();
        try {
            dao.getSharedCRUD().delete(dao.getSharedCRUD().findSharedByFileId(fileId, AccountFacade.getCurrentUser().getAccId()));
            dao.commit();
            dao = new DAO();
            System.out.println("File Id: " + fileId);
            File toDelete = (File) dao.getFileCRUD().read(fileId);
            System.out.println("File Name: " + toDelete.getFileName());
            dao.getFileCRUD().delete(toDelete);
            dao.commit();
            System.out.println("Success!");
        } catch (PersistException e) {
            System.out.println("Nqkuv Persist Exception metna");
        } catch (NoResultException ex) {
            System.out.println("Nqkuv NoResultException metna");
        }
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
