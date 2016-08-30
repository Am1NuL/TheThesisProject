package com.thesis.crud;

import com.thesis.model.File;
import com.thesis.model.Shared;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alex on 28-Aug-16.
 */
public class SharedCRUD extends GenericCRUD<Shared> {

    /**
     * Explicit constructor of the class.
     *
     * @param entityManager
     *            Entity manager to be used for the transactions.
     */
    public SharedCRUD(final EntityManager entityManager, final Logger logger) {
        super(entityManager, logger, Shared.class);
    }

    public List<Shared> findAllFilesByUser(UUID accId) {
        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Shared> query = builder.createQuery(Shared.class);
        final Root<Shared> root = query.from(Shared.class);

        query.select(root).where(builder.equal(root.get("accountId"), accId));

        return getEntityManager().createQuery(query).getResultList();
    }

    public Shared findSharedByAccId(UUID uuid) {
        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Shared> query = builder.createQuery(Shared.class);
        final Root<Shared> root = query.from(Shared.class);

        query.select(root).where(builder.equal(root.get("accountId"), uuid));

        return getEntityManager().createQuery(query).getSingleResult();
    }
}
