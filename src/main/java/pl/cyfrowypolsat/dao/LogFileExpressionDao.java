package pl.cyfrowypolsat.dao;

import java.util.List;

import org.hibernate.Session;

import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.LogfileExpression;
import pl.cyfrowypolsat.util.HibernateUtil;

public class LogFileExpressionDao {

	public static void save(LogfileExpression lfe){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try{
			session.saveOrUpdate(lfe);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		session.close();
	}
	
	public static List<LogfileExpression> getAll(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<LogfileExpression> lfe = session.createCriteria(LogfileExpression.class).list();
		session.close();
		return lfe;
	}
	
	public static List<LogfileExpression> getByApp(Application app){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<LogfileExpression> lfe = session.createQuery("SELECT l FROM LogfileExpression l WHERE l.application = :app")
				.setParameter("app", app).list();
		session.close();
		return lfe;
	}
	
	public static void delete(LogfileExpression lfe){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try{
			session.delete(lfe);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		session.close();
	}
}
