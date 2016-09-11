package com.thesis.controller;

import com.thesis.util.JSFMessageUtil;
import org.primefaces.context.RequestContext;

/**
 * Created by Alex on 21-Aug-16.
 */
public class AbstractBean {
    private static final String KEEP_DIALOG_OPENED = "KEEP_DIALOG_OPENED";

    public AbstractBean() {
        super();
    }

    protected void displayErrorMessageToUser(String message) {
        JSFMessageUtil messageUtil = new JSFMessageUtil();
        messageUtil.sendErrorMessageToUser(message);
    }

    protected void displayInfoMessageToUser(String message) {
        JSFMessageUtil messageUtil = new JSFMessageUtil();
        messageUtil.sendInfoMessageToUser(message);
    }
}
