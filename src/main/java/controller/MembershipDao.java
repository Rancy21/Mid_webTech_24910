package controller;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Membership;
import util.HibernateUtil;

public class MembershipDao {
	private static final Logger logger = Logger.getLogger(MembershipDao.class.getName());

    // Create
    public boolean createMembership( Membership membership) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.persist(membership);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error creating membership: " + e.getMessage());
            return false;
        }
    }

    // Read (Fetch by ID)
    public Membership getMembershipById(Long id) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            return session.get(Membership.class, id);
        } catch (Exception e) {
            logger.severe("Error fetching Membership by ID: " + e.getMessage());
            return null;
        }
    }

    // Update
    public boolean updateMembership(Membership membership) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.merge(membership);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error updating Membership: " + e.getMessage());
            return false;
        }
    }

    // Delete
    public boolean deleteMembership(Membership book) {
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

    // Fetch Membership by Title
//    public Membership getMembershipByName(String membershipName) {
//        try (Session session = HibernateUtil.getConnection().openSession()) {
//            String hql = "FROM Membership b WHERE b.membership_name = :title";
//            return session.createQuery(hql, Membership.class)
//                          .setParameter("title", membershipName)
//                          .uniqueResult();
//        } catch (Exception e) {
//            logger.severe("Error fetching Membership by title: " + e.getMessage());
//            return null;
//        }
//    }
    
    // Fetch All Memberships (Optional, useful for listing all records)
    public List<Membership> getAllMemberships() {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM Membership";
            return session.createQuery(hql, Membership.class).list();
        } catch (Exception e) {
            logger.severe("Error fetching all Memberships: " + e.getMessage());
            return null;
        }
    }
}
