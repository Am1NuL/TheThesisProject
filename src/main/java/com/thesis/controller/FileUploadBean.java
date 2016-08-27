package com.thesis.controller;

import com.thesis.crud.DAO;
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
    private byte[] fileData;


    public void fileUploadListener(FileUploadEvent e) throws IOException, PersistException {
        this.uploadedFile = e.getFile();

        String filename = FilenameUtils.getName(uploadedFile.getFileName());
        fileData = new byte[(int)uploadedFile.getSize()];
        DataInputStream dataInputStream = new DataInputStream(uploadedFile.getInputstream());
        dataInputStream.readFully(fileData);

        file = new File();
        file.setFileName(filename);
        file.setData(fileData);

        fileFacade = new FileFacade();
        fileFacade.upload(file);

        System.out.println("File Name: " + e.getFile().getFileName() + "File Size: " + e.getFile().getSize());
        displayInfoMessageToUser("File Uploaded Successfully !");


    }
}
