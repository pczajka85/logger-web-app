package pl.cyfrowypolsat.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import pl.cyfrowypolsat.dao.ApplicationDao;
import pl.cyfrowypolsat.dao.DeveloperDao;
import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.Developer;
import pl.cyfrowypolsat.entity.ErrorCount;
import pl.cyfrowypolsat.job.FileDownloader;
import pl.cyfrowypolsat.util.HibernateUtil;

public class Main {

	public static void main(String[] args) {
		HibernateUtil.createSessionFactory();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		try {
			List<Application> apps = ApplicationDao.getByErrorCounterDate(sdf.parse("25.01.2015"));
			for(Application app: apps){
//				System.out.println(app.getErrorCounts().size());
				for(ErrorCount ec : app.getErrorCounts()){
					System.out.println(ec.getErrorType()+" : "+ec.getCount()+" : " + ec.getDate());
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HibernateUtil.getSessionFactory().close();
	}
	
	private void fileDownload(){
		HibernateUtil.createSessionFactory();
		
		FileDownloader fd = new FileDownloader();
		fd.run();
		
		HibernateUtil.getSessionFactory().close();
	}
}
