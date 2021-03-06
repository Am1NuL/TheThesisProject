package com.thesis.crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Alex on 19-Aug-16.
 */
public final class DAO {
    /**
     * The Entity Manager Factory that will be used for the transaction.
     */
    private static final EntityManagerFactory EMF = Persistence
            .createEntityManagerFactory("thesis");

    /**
     * The entity manager that will be used to control the entities.
     */
    private EntityManager em;

    /**
     * A constructor for the DAO class. Begins the transaction and creates an
     * entity manager.
     */
    public DAO() {
        this.em = EMF.createEntityManager();
        this.em.getTransaction().begin();
    }

    /**
     * The AccountCRUD that will be used to Create, Read, Update and Delete
     * accounts.
     */
    private AccountCRUD accountCRUD;

    /**
     * The FileCRUD that will be used to Create, Read, Update and Delete
     * accounts.
     */
    private FileCRUD fileCRUD;

    /**
     * The SharedCRUD that will be used to Create, Read, Update and Delete
     * accounts.
     */
    private SharedCRUD sharedCRUD;

    /**
     * A getter for the AccountRUD field. If there isn't one a AccountCRUD is
     * created.
     *
     * @return Returns the AccountCRUD field.
     */
    public AccountCRUD getAccountCRUD() {
        if (this.accountCRUD == null) {
            this.accountCRUD = new AccountCRUD(em);
        }
        return this.accountCRUD;
    }

    /**
     * A getter for the fileCRUD field. If there isn't one a AccountCRUD is
     * created.
     *
     * @return Returns the fileCRUD field.
     */
    public FileCRUD getFileCRUD() {
        if (this.fileCRUD == null) {
            this.fileCRUD = new FileCRUD(em);
        }
        return this.fileCRUD;
    }

    public SharedCRUD getSharedCRUD() {
        if (this.sharedCRUD == null) {
            this.sharedCRUD = new SharedCRUD(em);
        }
        return this.sharedCRUD;
    }

    public void closeTransaction() {
        this.em.close();
    }

    public void commit() {
        this.em.getTransaction().commit();
        this.em.close();
    }

    /**
     * A method that rollbacks the transaction and closes the entity manager.
     *
     */
    public void rollBack() {
        this.em.getTransaction().rollback();
        this.em.close();
    }

    /**
     * A method that detaches the object from the entity manager after which the
     * entity manager is no longer responsible for the object.
     *
     * @param toDetach
     *            The object that will be detached within this transaction.
     */
    public void detachEntity(final Object toDetach) {
        em.detach(toDetach);
    }
}
