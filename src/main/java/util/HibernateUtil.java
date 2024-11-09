package util;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (HibernateException e) {
			// TODO: handle exception
			System.err.println("Session Factory creation failed: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getConnection() {
		return sessionFactory;
	}
	
	public static void shutdown() {
		getConnection().close();
	}
}
