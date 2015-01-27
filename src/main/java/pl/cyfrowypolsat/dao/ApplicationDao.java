package pl.cyfrowypolsat.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.ErrorCount;
import pl.cyfrowypolsat.util.HibernateUtil;

public class ApplicationDao {

	public static void save(Application appication) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try {
			session.saveOrUpdate(appication);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		session.close();
	}

	@SuppressWarnings("unchecked")
	public static List<Application> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Application> apps = session
				.createQuery("SELECT a FROM Application a").list();
		session.close();

		return apps;
	}

	public static void delete(Application app) throws ConstraintViolationException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try {
			session.delete(app);
			session.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();
			throw e;
//			e.printStackTrace();
		}

		session.close();
	}
	
	public static void setStarted(Application app, boolean switcher){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try {
			app.setStarted(switcher);
			session.update(app);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		session.close();
	}
	
	public static Application find(long id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Application app = (Application) session.get(Application.class, id);
		session.close();
		return app;
	}
	
	public static List<Application> getByErrorCounterDate(Date date){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Application> apps = session.createQuery(
				"SELECT DISTINCT a FROM Application a JOIN FETCH a.errorCounts ec WHERE ec.date = :date")
		.setParameter("date", date).list();
		session.close();
		return apps;
	}
}
