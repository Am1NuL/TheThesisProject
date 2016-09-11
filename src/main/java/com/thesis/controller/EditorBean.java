package com.thesis.controller;

import com.thesis.crud.PersistException;
import com.thesis.facade.AccountFacade;
import com.thesis.facade.FileFacade;
import com.thesis.entities.File;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.RFonts;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Alex on 30-Aug-16.
 */
@Named
@SessionScoped
public class EditorBean extends AbstractBean implements Serializable{

    private static final long serialVersionUID = 20111020L;

    private String content;
    private String editorName;
    /*private byte[] data;
    private String encodedString;*/
    private FileFacade fileFacade;

    public EditorBean() {
        content = "Write here";
    }

    public void saveListener() throws Docx4JException, JAXBException, IOException, PersistException {
        /*System.out.println("CONTENT: " + getContent());
        data = getContent().getBytes();
        File editorContent = new File();
        editorContent.setFileName("temp.html");
        editorContent.setData(data);
        System.out.println("File Name: " + editorContent.getFileName());
        System.out.println("File Data Decoded: " + editorContent.getData());
        encodedString = new String(data);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("File Name: " + editorContent.getFileName());
        System.out.println("File Data Encoded: " + encodedString);*/


        String baseURL = "https://github.com/plutext/docx4j-ImportXHTML";

        // Setup font mapping
        RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
        rfonts.setAscii("Century Gothic");
        XHTMLImporterImpl.addFontMapping("Century Gothic", rfonts);

        // Create an empty docx package
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();


        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
        wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
        ndp.unmarshalDefaultNumbering();

        // Convert the XHTML, and add it into the empty docx we made
        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);

        String myContent = "<div>" + getContent() + "</div>";

        XHTMLImporter.setHyperlinkStyle("Hyperlink");
        wordMLPackage.getMainDocumentPart().getContent().addAll(
                XHTMLImporter.convert(myContent, baseURL) );

        System.out.println(
                XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement(), true, true));

        java.io.File localFile = new java.io.File("D:\\workspace\\dafaq.docx");

        wordMLPackage.save(localFile);

        Path path = Paths.get(localFile.getAbsolutePath());
        byte[] data = Files.readAllBytes(path);

        System.out.println("FILE NAME " + editorName);
        File persistFile = new File();
        persistFile.setFileName(editorName + ".docx");
        persistFile.setData(data);
        persistFile.setOwner(AccountFacade.getCurrentUser().getUsername());
        getFileFacade().upload(persistFile);
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        if(editorName == null) {
            displayErrorMessageToUser("Enter file name !");
        }
        this.editorName = editorName;
    }

    public FileFacade getFileFacade() {
        if(fileFacade == null) {
            fileFacade = new FileFacade();
        }

        return fileFacade;
    }
}
