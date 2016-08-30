package com.thesis.controller;

import com.thesis.crud.PersistException;
import com.thesis.facade.FileFacade;
import com.thesis.model.Account;
import com.thesis.model.File;
import com.thesis.model.Shared;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Alex on 28-Aug-16.
 */
@Named("fileBean")
@SessionScoped
public class FileBean extends AbstractBean implements Serializable{

    private List<File> files;
    private FileFacade fileFacade;
    private Set<File> filesSet;
    private UUID currentFileId;

    public FileFacade getFileFacade() {
        if(fileFacade == null) {
            fileFacade = new FileFacade();
        }

        return fileFacade;
    }

    // TODO: Close dialog, Reload smth after using share?, Setting same file?
    public void shareFile(String userId) {
        try {
            System.out.println("Current File Id: " + getCurrentFileId() + "~~~~~~~~~~~~~~~~~~~~~~~~~~");
            getFileFacade().share(userId, getCurrentFileId());
        } catch (Exception e) {
        }
    }

    public Set<File> getAllFilesByUser() {
        filesSet = getFileFacade().listAllFilesByUser();
        return filesSet;
    }

    public List<File> getAllFiles() {
        loadFiles();
        return files;
    }

    public void loadFiles() { files =  getFileFacade().listAll(); }

    public UUID getCurrentFileId() {
        return currentFileId;
    }

    public void setCurrentFileId(UUID currentFileId) {
        this.currentFileId = currentFileId;
    }
}
