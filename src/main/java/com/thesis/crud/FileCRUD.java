package com.thesis.crud;

import com.mysql.jdbc.Blob;
import com.thesis.model.File;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

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

    public File findFileById(UUID fileId) {
        if (fileId == null || ("").equals(fileId)) {
            throw new IllegalArgumentException("UUID to search cannot be null or empty.");
        }
        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<File> query = builder.createQuery(File.class);
        final Root<File> root = query.from(File.class);

        query.select(root).where(builder.equal(root.get("fileId"), fileId));

        return getEntityManager().createQuery(query).getSingleResult();
    }

    public List<File> findAll() {
        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<File> query = builder.createQuery(File.class);
        final Root<File> root = query.from(File.class);
        query.select(root);

        return getEntityManager().createQuery(query).getResultList();
    }
}
