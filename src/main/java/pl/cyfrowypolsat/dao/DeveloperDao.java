package pl.cyfrowypolsat.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.Session;

import pl.cyfrowypolsat.entity.Developer;
import pl.cyfrowypolsat.util.HibernateUtil;

public class DeveloperDao {

	@SuppressWarnings("unchecked")
	public static List<Developer> getAllDevelopers(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Developer> developers = session.createCriteria(Developer.class).list();
		session.close();
		return developers;
	}
	
	public static Developer getOneByUsernameAndPassword(String username, String password){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Developer developer = (Developer) session.createQuery(
				"SELECT d FROM Developer d WHERE d.username = :username AND d.password = :password")
		.setParameter("username", username)
		.setParameter("password", md5(password))
		.uniqueResult();
		
		session.close();
		return developer;
	}
		
	public static String md5(String input){
		String result = input;
        if(input != null) {
            MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
				md.update(input.getBytes());
	            BigInteger hash = new BigInteger(1, md.digest());
	            result = hash.toString(16);
	            if ((result.length() % 2) != 0) {
	                result = "0" + result;
	            }
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
            
        }
        return result;
	}
}
