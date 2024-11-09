package controller;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.MembershipType;
import util.HibernateUtil;

public class MembershipTypeDao {
	private static final Logger logger = Logger.getLogger(MembershipTypeDao.class.getName());

    // Create
    public boolean createMembershipType( MembershipType membershipType) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.persist(membershipType);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error creating membershipType: " + e.getMessage());
            return false;
        }
    }

    // Read (Fetch by ID)
    public MembershipType getMembershipTypeById(Long id) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            return session.get(MembershipType.class, id);
        } catch (Exception e) {
            logger.severe("Error fetching MembershipType by ID: " + e.getMessage());
            return null;
        }
    }

    // Update
    public MembershipType updateMembershipType(MembershipType membershipType) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getConnection().openSession()) {
            trans = session.beginTransaction();
            session.merge(membershipType);
            trans.commit();
            return membershipType;
        } catch (Exception e) {
            if (trans != null) trans.rollback();
            logger.severe("Error updating MembershipType: " + e.getMessage());
            return null;
        }
    }

    // Delete
    public boolean deleteMembershipType(MembershipType book) {
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

    // Fetch MembershipType by Title
    public MembershipType getMembershipTypeByName(String membershipTypeName) {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM MembershipType b WHERE b.name = :title";
            return session.createQuery(hql, MembershipType.class)
                          .setParameter("title", membershipTypeName)
                          .uniqueResult();
        } catch (Exception e) {
            logger.severe("Error fetching MembershipType by title: " + e.getMessage());
            return null;
        }
    }
    
    // Fetch All MembershipTypes (Optional, useful for listing all records)
    public List<MembershipType> getAllMembershipTypes() {
        try (Session session = HibernateUtil.getConnection().openSession()) {
            String hql = "FROM MembershipType";
            return session.createQuery(hql, MembershipType.class).list();
        } catch (Exception e) {
            logger.severe("Error fetching all MembershipTypes: " + e.getMessage());
            return null;
        }
    }
}
