package jp.co.sukky.examples.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import jp.co.sukky.examples.model.PersonModel;

/**
 * PersonDAO
 */
public class PersonDao {

    /** DAO command for SELECT ALL. */
    private static final String DAO_CMD_SELECT_ALL = "Person.selectAll";

    /** DAO command for SELECT by ID. */
    private static final String DAO_CMD_SELECT_BY_ID = "Person.selectById";

    /** DAO command for INSERT. */
    private static final String DAO_CMD_INSERT = "Person.insert";

    /** DAO command for UPDATE. */
    private static final String DAO_CMD_UPDATE = "Person.update";

    /** DAO command for DELETE. */
    private static final String DAO_CMD_DELETE = "Person.delete";

    /** DAO command for SELECT DELETE ALL. */
    private static final String DAO_CMD_DELETE_ALL = "Person.deleteAll";

    /**
     * SqlSessionFactory.
     */
    private SqlSessionFactory sqlSessionFactory = null;

    /**
     * Constructor.
     * 
     * @param sqlSessionFactory instance of SqlSessionFactory 
     */
    public PersonDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Returns the list of all Person instances from the database.
     * 
     * @return the list of all Person instances from the database.
     */
    public List<PersonModel> selectAll() {
        List<PersonModel> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            list = session.selectList(DAO_CMD_SELECT_ALL);
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Select instance of Person from the database.
     * 
     * @param id the instance to be persisted.
     * @return person person object
     */
    public PersonModel selectById(int id) {
        PersonModel person = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            person = session.selectOne(DAO_CMD_SELECT_BY_ID, id);
        } finally {
            session.close();
        }
        return person;
    }

    /**
     * Insert an instance of Person into the database.
     * 
     * @param person the instance to be persisted.
     * @return person id
     */
    public int insert(PersonModel person) {
        int id = -1;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            id = session.insert(DAO_CMD_INSERT, person);
        } finally {
            session.commit();
            session.close();
        }
        return id;
    }

    /**
     * Update an instance of Person into the database.
     * 
     * @param person
     *            the instance to be persisted.
     */
    public void update(PersonModel person) {
        int id = -1;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            id = session.update(DAO_CMD_UPDATE, person);

        } finally {
            session.commit();
            session.close();
        }
    }

    /**
     * Delete an instance of Person from the database.
     * 
     * @param id
     *            value of the instance to be deleted.
     */
    public void delete(int id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.delete(DAO_CMD_DELETE, id);
        } finally {
            session.commit();
            session.close();
        }
    }

    /**
     * Return the list of all Person instances from the database.
     * 
     * @return the list of all Person instances from the database.
     */
    public void deleteAll() {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.delete(DAO_CMD_DELETE_ALL);
        } finally {
            session.commit();
            session.close();
        }
    }
}