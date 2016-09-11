package com.thesis.controller;

import com.thesis.facade.FileFacade;
import com.thesis.entities.File;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.activation.MimetypesFileTypeMap;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Alex on 28-Aug-16.
 */
@Named("fileDownload")
@SessionScoped
public class FileDownloadBean extends AbstractBean implements Serializable {
    private FileFacade fileFacade;
    private StreamedContent downFile;
    private File file;

    public FileFacade getFileFacade() {
        if(fileFacade == null) {
            fileFacade = new FileFacade();
        }

        return fileFacade;
    }

    public StreamedContent fileDownloadListener(UUID fileId) {
        try{
            file = getFileFacade().getFile(fileId);
        }catch (NoResultException e) {
            System.out.println("NoResultException ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }

        InputStream is = new ByteArrayInputStream(file.getData());
        downFile = new DefaultStreamedContent(is, new MimetypesFileTypeMap().getContentType(file.getFileName()) , file.getFileName());
        System.out.println("File Name: " + downFile.getName());
        displayInfoMessageToUser("File Downloaded Successfully !");

        return downFile;
    }
}
