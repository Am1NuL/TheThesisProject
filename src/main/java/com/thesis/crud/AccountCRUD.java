package com.thesis.crud;

import com.thesis.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Alex on 18-Aug-16.
 */
public final class AccountCRUD extends GenericCRUD<Account> {
    /**
     * Explicit constructor of the class.
     *
     * @param entityManager
     *            Entity manager to be used for the transactions.
     */
    public AccountCRUD(final EntityManager entityManager) {
        super(entityManager, Account.class);
    }

    public Account findUserByEmail(String email) {
        if (email == null || ("").equals(email)) {
            throw new IllegalArgumentException("String to search cannot be null or empty.");
        }
        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Account> query = builder.createQuery(Account.class);
        final Root<Account> root = query.from(Account.class);

        query.select(root).where(builder.equal(root.get("email"), email));

        return getEntityManager().createQuery(query).getSingleResult();
    }

    public Account findUserByUsername(String username) {
        if (username == null || ("").equals(username)) {
            throw new IllegalArgumentException("String to search cannot be null or empty.");
        }
        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Account> query = builder.createQuery(Account.class);
        final Root<Account> root = query.from(Account.class);

        query.select(root).where(builder.like(root.<String>get("username"), username));

        return getEntityManager().createQuery(query).getSingleResult();
    }
}
