package controller;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.User;
import util.HibernateUtil;

public class UserDao {
	
	private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    // Create
	public boolean createUser(User user) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getConnection().openSession()) {
	        transaction = session.beginTransaction();
	        
	        // Persist user
	        session.persist(user);
	        
	        transaction.commit();
	        return true;
	    } catch (Exception e) {
	        if (transaction != null) transaction.rollback();
	        logger.severe("Error creating user: " + e.getMessage());
	        return false;
	    }
	}

    // Read (Fetch by ID)
    public User getUserById(Long id) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            logger.severe("Error fetching User by ID: " + e.getMessage());
            return null;
        }
    }

    // Update
    public User updateUser(User user) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.merge(user);
            trans.commit();
            return user;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error updating User: " + e.getMessage());
            return null;
        }
    }

    // Delete
    public boolean deleteUser(User user) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
                session.delete(user);
                trans.commit();
                return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error deleting User: " + e.getMessage());
            return false;
        }
    }

    // Fetch User by Title
    public User getUserByName(String userName) {
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
        	transaction = session.beginTransaction();
            String hql = "FROM User b WHERE b.userName = :userName"; // Ensure this matches your User class property
            User user = session.createQuery(hql, User.class) // This form is correct for typed queries in Hibernate 6.0
                          .setParameter("userName", userName)
                          .setMaxResults(1) // Ensures only one result is returned
                          .uniqueResult();
            transaction.commit();
            return user;
        } catch (Exception e) {
        	if (transaction != null) transaction.rollback();
            logger.severe("Error fetching User by username: " + e.getMessage());
            return null;
        }
    }


    
    // Fetch All Users (Optional, useful for listing all records)
    public List<User> getAllUsers() {
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
        	transaction = session.beginTransaction();
            String hql = "FROM User";
            List<User> users = session.createQuery(hql, User.class).list();
            transaction.commit();
            return users;
        } catch (Exception e) {
        	if (transaction != null) transaction.rollback();
            logger.severe("Error fetching all Users: " + e.getMessage());
            return null;
        }
    }
}
