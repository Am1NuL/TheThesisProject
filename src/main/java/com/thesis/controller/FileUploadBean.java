package com.thesis.controller;

import com.thesis.crud.PersistException;
import com.thesis.facade.FileFacade;
import com.thesis.model.File;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.*;

/**
 * Created by Alex on 25-Aug-16.
 */
@Named("fileUpload")
@SessionScoped
public class FileUploadBean extends AbstractBean implements Serializable {
    private FileFacade fileFacade;
    private UploadedFile uploadedFile;
    private File file;

    public FileFacade getFileFacade() {
        if(fileFacade == null) {
            fileFacade = new FileFacade();
        }

        return fileFacade;
    }

    public void fileUploadListener(FileUploadEvent e) throws IOException, PersistException {
        try{
            this.uploadedFile = e.getFile();

            String filename = FilenameUtils.getName(uploadedFile.getFileName());

            file = new File();
            file.setFileName(filename);
            file.setData(uploadedFile.getContents());

            getFileFacade().upload(file);

            System.out.println("File Name: " + e.getFile().getFileName() + " File Size: " + e.getFile().getSize());
            displayInfoMessageToUser("File Uploaded Successfully!");
        }catch (Exception ex) {

        }
    }
}
