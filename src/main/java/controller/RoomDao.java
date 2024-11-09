package controller;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Room;
import util.HibernateUtil;

public class RoomDao {
	private static final Logger logger = Logger.getLogger(RoomDao.class.getName());

    // Create
    public boolean createRoom( Room room) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.persist(room);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error creating room: " + e.getMessage());
            return false;
        }
    }

    // Read (Fetch by ID)
    public Room getRoomById(Long id) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            return session.get(Room.class, id);
        } catch (Exception e) {
            logger.severe("Error fetching Room by ID: " + e.getMessage());
            return null;
        }
    }

    // Update
    public Room updateRoom(Room room) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.merge(room);
            trans.commit();
            return room;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error updating Room: " + e.getMessage());
            return null;
        }
    }

    // Delete
    public boolean deleteRoom(Room book) {
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

    // Fetch Room by Title
    public Room getRoomByCode(String roomCode) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM Room b WHERE b.roomCode = :title";
            return session.createQuery(hql, Room.class)
                          .setParameter("title", roomCode)
                          .uniqueResult();
        } catch (Exception e) {
            logger.severe("Error fetching Room by title: " + e.getMessage());
            return null;
        }
    }
    
    // Fetch All Rooms (Optional, useful for listing all records)
    public List<Room> getAllRooms() {
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
        	transaction = session.beginTransaction();
            String hql = "FROM Room";
            List<Room> rooms = session.createQuery(hql, Room.class).list();
            transaction.commit();
            return rooms;
        } catch (Exception e) {
            logger.severe("Error fetching all Rooms: " + e.getMessage());
            return null;
        }
    }
}
