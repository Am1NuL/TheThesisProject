package com.thesis.crud;

import com.thesis.model.File;

import javax.persistence.EntityManager;

/**
 * Created by Alex on 25-Aug-16.
 */
public final class FileCRUD extends GenericCRUD {
    /**
     * Explicit constructor of the class.
     *
     * @param entityManager
     *            Entity manager to be used for the transactions.
     */
    public FileCRUD(final EntityManager entityManager) {
        super(entityManager, File.class);
    }
}
