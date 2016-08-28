package com.thesis.controller;

import com.thesis.facade.FileFacade;
import com.thesis.model.File;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Alex on 28-Aug-16.
 */
@Named("fileBean")
@SessionScoped
public class FileBean implements Serializable{

    private List<File> files;
    private FileFacade fileFacade;

    public FileFacade getFileFacade() {
        if(fileFacade == null) {
            fileFacade = new FileFacade();
        }

        return fileFacade;
    }

    public List<File> getAllFiles() {
        if(files == null) {
            loadFiles();
        }

        return files;
    }

    public void loadFiles() { files =  getFileFacade().listAll(); }
}
