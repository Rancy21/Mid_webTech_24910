package controller;

import org.hibernate.Session;
import org.hibernate.Transaction;
import model.Book;
import util.HibernateUtil;
import java.util.logging.Logger;
import java.util.List;

public class BookDao {
    private static final Logger logger = Logger.getLogger(BookDao.class.getName());

    // Create
    public boolean createBook(Book book) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.persist(book);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error creating book: " + e.getMessage());
            return false;
        }
    }

    // Read (Fetch by ID)
    public Book getBookById(Long id) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            return session.get(Book.class, id);
        } catch (Exception e) {
            logger.severe("Error fetching book by ID: " + e.getMessage());
            return null;
        }
    }

    // Update
    public boolean updateBook(Book book) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.merge(book);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error updating book: " + e.getMessage());
            return false;
        }
    }

    // Delete
    public boolean deleteBook(Book book) {
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

    // Fetch Book by Title
    public Book getBookByTitle(String title) {
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
        	transaction =session.beginTransaction();
            String hql = "FROM Book b WHERE b.title = :title";
            Book book = session.createQuery(hql, Book.class)
                          .setParameter("title", title)
                          .uniqueResult();
            transaction.commit();
            return book;
        } catch (Exception e) {
            logger.severe("Error fetching book by title: " + e.getMessage());
            return null;
        }
    }
    
    // Fetch All Books (Optional, useful for listing all records)
    public List<Book> getAllBooks() {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM Book";
            return session.createQuery(hql, Book.class).list();
        } catch (Exception e) {
            logger.severe("Error fetching all books: " + e.getMessage());
            return null;
        }
    }
}
