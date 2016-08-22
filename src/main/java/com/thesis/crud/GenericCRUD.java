package com.thesis.crud;

import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * Created by Alex on 18-Aug-16.
 */
public abstract class GenericCRUD<T> {
    /**
     * An entity manager that will handle the operations.
     */
    private EntityManager entityManager;

    /**
     *
     */
    private final Class<?> entityType;

    /**
     * A constructor for the TCRUD class.
     *
     * @param entityManager
     *            The entity manager that will handle the operations.
     * @param entityType
     *            class type
     */
    public GenericCRUD(final EntityManager entityManager, final Class<?> entityType) {
        this.setEntityManager(entityManager);
        this.entityType = entityType;
    }

    /**
     * Interface implemented by all command.
     */
    private interface Executable {
        /**
         * A method used to be implemented in all classes using the current
         * interface.
         */
        void execute();
    }

    /**
     * A class that will be responsible for creating row in T table.
     */
    private class CreateCommand implements Executable {
        /**
         * This member save current entity.
         */
        private final T entity;

        /**
         * Class constructor specifying Entity to create.
         *
         * @param entity
         *            Entity object to create CreateCommand object.
         */
        CreateCommand(final T entity) {
            this.entity = entity;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void execute() {
            getEntityManager().persist(this.entity);
        }
    }

    /**
     * A class that will be responsible for update row in T table.
     */
    private class UpdateCommand implements Executable {
        /**
         * This member save current entity.
         */
        private T entity;

        /**
         * Class constructor specifying entity to create.
         *
         * @param entity
         *            Entity object to create UpdateCommand object.
         */
        UpdateCommand(final T entity) {
            this.entity = entity;

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void execute() {
            getEntityManager().merge(entity);
        }

        /**
         * Return current entity.
         *
         * @return current entity.
         */
        public T getUpdated() {
            return this.entity;
        }
    }

    /**
     * A class that will execute the actual deletion of a row.
     */
    private class DeleteCommand implements Executable {
        /**
         * This member save current entity.
         */
        private final T entity;

        /**
         * Class constructor specifying entity to be deleted.
         *
         * @param entity
         *            Entity object that will be deleted.
         */
        DeleteCommand(final T entity) {
            this.entity = entity;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void execute() {
            getEntityManager().remove(entity);
        }
    }

    /**
     * A class containing a command for reading a row from a table.
     */
    private class ReadCommand implements Executable {
        /**
         * The row that will be returned when read.
         */
        private T toReturn;
        /**
         * The primary key of the row that will be read.
         */
        private final UUID primaryKey;

        /**
         * A constructor that will create the read command via the primary key
         * of the row that will be read.
         *
         * @param primaryKey
         *            The primary key of the row that will be read.
         */
        ReadCommand(final UUID primaryKey) {
            this.primaryKey = primaryKey;
        }

        /**
         * The method that will do the actual reading from the table.
         */
        @SuppressWarnings("unchecked")
        @Override
        public void execute() {
            toReturn = (T) getEntityManager().find(entityType, primaryKey);
        }

        /**
         * A getter for the field which contains the row that will be read.
         *
         * @return Returns the row that will be read.
         */
        public T getResult() {
            return this.toReturn;
        }
    }

    /**
     * A method that will execute a command and check for PersistanceExceptions.
     * Re throws new exception(PersistException).
     *
     * @param command
     *            The command that will be executed.
     * @throws PersistException
     *             Throws an exception if the persistence has failed.
     */
    private void executeWithExceptionHandling(final Executable command) throws PersistException {
        try {
            command.execute();
        }
        catch (javax.persistence.PersistenceException e) {
            throw new PersistException("PersistException", e);
        }
    }

    /**
     * A method that will create a new row in the T table.
     *
     * @param entity
     *            The new entity object.
     * @throws PersistException
     *             Throws an exception if the creation fails.
     */
    public void create(final T entity) throws PersistException {
        executeWithExceptionHandling(new CreateCommand(entity));
    }

    /**
     * A method that will update an existing row in the T table.
     *
     * @param entity
     *            The new entity that will replace the existing row.
     * @return Returns the updated row.
     * @throws PersistException
     *             Throws an exception if the update fails.
     */
    public T update(final T entity) throws PersistException {
        final UpdateCommand toUpdate = new UpdateCommand(entity);
        executeWithExceptionHandling(toUpdate);
        return toUpdate.getUpdated();
    }

    /**
     * A method that will select an existing row in the T table.
     *
     * @param primaryKey
     *            The primary key of the row that will be selected.
     * @return Returns the T that is selected.
     * @throws PersistException
     *             Throws an exception if the selection fails.
     */
    public T read(final UUID primaryKey) throws PersistException {
        final T result;
        final ReadCommand read = new ReadCommand(primaryKey);

        executeWithExceptionHandling(read);
        result = read.getResult();
        return result;
    }

    /**
     * A method that will delete an existing row in the T table.
     *
     * @param entity
     *            The T that will be deleted.
     * @throws PersistException
     *             Throws an exception if the deletion fails.
     */
    public void delete(final T entity) throws PersistException {
        executeWithExceptionHandling(new DeleteCommand(entity));
    }

    /**
     * A getter for the entity manager.
     *
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * A setter for the entity manager.
     *
     * @param entityManager
     *            the entityManager to set
     */
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}

