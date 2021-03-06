package pl.cyfrowypolsat.dao;

import java.util.List;

import org.hibernate.Session;

import pl.cyfrowypolsat.entity.ErrorCount;
import pl.cyfrowypolsat.util.HibernateUtil;

public class ErrorCounterDao {

	public List<ErrorCount> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ErrorCount> errorCountList = session.createQuery("FROM ErrorCount e").list();
		session.close();
		return errorCountList;
	}
	
	public static void save(ErrorCount ec) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try {
			session.saveOrUpdate(ec);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		session.close();
	}
}
