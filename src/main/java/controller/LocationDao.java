package controller;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Location;
import util.HibernateUtil;

public class LocationDao {
	private static final Logger logger = Logger.getLogger(LocationDao.class.getName());

    // Create
    public boolean createLocation( Location location) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.persist(location);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error creating location: " + e.getMessage());
            return false;
        }
    }

    // Read (Fetch by ID)
    public Location getLocationById(Long id) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            return session.get(Location.class, id);
        } catch (Exception e) {
            logger.severe("Error fetching location by ID: " + e.getMessage());
            return null;
        }
    }

    // Update
    public Location updateLocation(Location location) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.merge(location);
            trans.commit();
            return location;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error updating location: " + e.getMessage());
            return null;
        }
    }

    // Delete
    public boolean deleteLocation(Location book) {
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

    // Fetch Location by Title
    public Location getLocationByCode(String locationCode) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM Location b WHERE b.locationCode = :title";
            return session.createQuery(hql, Location.class)
                          .setParameter("title", locationCode)
                          .uniqueResult();
        } catch (Exception e) {
            logger.severe("Error fetching Location by title: " + e.getMessage());
            return null;
        }
    }
    
    public Location getLocationByName(String locationName) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM Location b WHERE b.locationCode = :title";
            return session.createQuery(hql, Location.class)
                          .setParameter("title", locationName)
                          .uniqueResult();
        } catch (Exception e) {
            logger.severe("Error fetching Location by title: " + e.getMessage());
            return null;
        }
    }
    
    // Fetch All Locations (Optional, useful for listing all records)
    public List<Location> getAllLocations() {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM Location";
            return session.createQuery(hql, Location.class).list();
        } catch (Exception e) {
            logger.severe("Error fetching all Locations: " + e.getMessage());
            return null;
        }
    }
}
