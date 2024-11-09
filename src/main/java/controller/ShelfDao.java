package controller;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Shelf;
import util.HibernateUtil;

public class ShelfDao {
	private static final Logger logger = Logger.getLogger(ShelfDao.class.getName());

    // Create
    public boolean createShelf( Shelf shelf) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.persist(shelf);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error creating shelf: " + e.getMessage());
            return false;
        }
    }

    // Read (Fetch by ID)
    public Shelf getShelfById(Long id) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            return session.get(Shelf.class, id);
        } catch (Exception e) {
            logger.severe("Error fetching Shelf by ID: " + e.getMessage());
            return null;
        }
    }

    // Update
    public boolean updateShelf(Shelf shelf) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.merge(shelf);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error updating Shelf: " + e.getMessage());
            return true;
        }
    }

    // Delete
    public boolean deleteShelf(Shelf book) {
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

    // Fetch Shelf by Title
    public Shelf getShelfByCode(String shelfCode) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM Shelf b WHERE b.bookCategory = :title";
            Shelf shelf = session.createQuery(hql, Shelf.class)
                                 .setParameter("title", shelfCode)
                                 .uniqueResult();
            transaction.commit();
            return shelf;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.severe("Error fetching Shelf by Code: " + e.getMessage());
            return null;
        }
    }
    
    // Fetch All Shelfs (Optional, useful for listing all records)
    public List<Shelf> getAllShelfs() {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM Shelf";
            return session.createQuery(hql, Shelf.class).list();
        } catch (Exception e) {
            logger.severe("Error fetching all Shelfs: " + e.getMessage());
            return null;
        }
    }
}
