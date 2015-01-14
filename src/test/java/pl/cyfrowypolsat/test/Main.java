package pl.cyfrowypolsat.test;

import org.hibernate.Session;

import pl.cyfrowypolsat.dao.DeveloperDao;
import pl.cyfrowypolsat.entity.Developer;
import pl.cyfrowypolsat.job.FileDownloader;
import pl.cyfrowypolsat.util.HibernateUtil;

public class Main {

	public static void main(String[] args) {
//		System.out.println(DeveloperDao.md5("xxx"));
		HibernateUtil.createSessionFactory();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Developer d = (Developer) session.createQuery("FROM Developer d").uniqueResult();
		String s1 = d.getPassword();
		String s2;
		s2 = DeveloperDao.md5("xxsx");
		System.out.println(s1);
		System.out.println(s2);			
		System.out.println("ARE EQUALS = " + s1.equals(s2));
		
		HibernateUtil.getSessionFactory().close();
	}
	
	private void fileDownload(){
		HibernateUtil.createSessionFactory();
		
		FileDownloader fd = new FileDownloader();
		fd.run();
		
		HibernateUtil.getSessionFactory().close();
	}
}
