package controller;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Borrower;
import util.HibernateUtil;

public class BorrowerDao {
	private static final Logger logger = Logger.getLogger(BorrowerDao.class.getName());

    // Create
    public boolean createBorrower( Borrower borrower) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.persist(borrower);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error creating Borrower: " + e.getMessage());
            return false;
        }
    }

    // Read (Fetch by ID)
    public Borrower getBorrowerById(Long id) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            return session.get(Borrower.class, id);
        } catch (Exception e) {
            logger.severe("Error fetching Borrower by ID: " + e.getMessage());
            return null;
        }
    }

    // Update
    public boolean updateBorrower(Borrower borrower) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.merge(borrower);
            trans.commit();
            return true;
        } catch (Exception e) {
            logger.severe("Error updating Borrower: " + e.getMessage());
            return false;
        }
    }

    // Delete
    public boolean deleteBorrower(Borrower book) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
                session.delete(book);
                trans.commit();
                return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error deleting book: " + e.getMessage());
            return false;
        }
    }

    // Fetch Borrower by Title
//    public Borrower getBorrowerByName(String BorrowerName) {
//        try (Session session = HibernateUtil.getConnection().openSession()) {
//            String hql = "FROM Borrower b WHERE b.Borrower_name = :title";
//            return session.createQuery(hql, Borrower.class)
//                          .setParameter("title", BorrowerName)
//                          .uniqueResult();
//        } catch (Exception e) {
//            logger.severe("Error fetching Borrower by title: " + e.getMessage());
//            return null;
//        }
//    }
    
    // Fetch All Borrowers (Optional, useful for listing all records)
    public List<Borrower> getAllBorrowers() {
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
        	transaction = session.beginTransaction();
            String hql = "FROM Borrower";
            List<Borrower> bors = session.createQuery(hql, Borrower.class).list();
            transaction.commit();
            return bors;
        } catch (Exception e) {
            logger.severe("Error fetching all Borrowers: " + e.getMessage());
            return null;
        }
    }
}
