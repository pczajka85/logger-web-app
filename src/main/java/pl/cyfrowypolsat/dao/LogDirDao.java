package pl.cyfrowypolsat.dao;

import java.util.List;

import org.hibernate.Session;

import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.LogDir;
import pl.cyfrowypolsat.util.HibernateUtil;

public class LogDirDao {

	public static void save(LogDir logDir){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try{
			session.saveOrUpdate(logDir);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		session.close();
	}
	
	public static List<LogDir> getAll(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<LogDir> ld = session.createCriteria(LogDir.class).list();
		session.close();
		return ld;
	}
	
	public static List<LogDir> getByApplication(Application app){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<LogDir> ld = session.createQuery("SELECT l FROM LogDir l WHERE l.application = :app").setParameter("app", app).list();
		session.close();
		return ld;
	}
	
	public static void delete(LogDir ld){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try{
			session.delete(ld);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		session.close();
	}
}
