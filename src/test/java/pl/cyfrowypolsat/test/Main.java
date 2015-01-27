package pl.cyfrowypolsat.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

import pl.cyfrowypolsat.dao.DeveloperDao;
import pl.cyfrowypolsat.entity.Developer;
import pl.cyfrowypolsat.job.FileDownloader;
import pl.cyfrowypolsat.util.HibernateUtil;

public class Main {

	public static void main(String[] args) {
		String date = "24.01.2015"; 
		Date thisDate;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		try {
			thisDate = sdf.parse(date);
			System.out.println(thisDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void fileDownload(){
		HibernateUtil.createSessionFactory();
		
		FileDownloader fd = new FileDownloader();
		fd.run();
		
		HibernateUtil.getSessionFactory().close();
	}
}
