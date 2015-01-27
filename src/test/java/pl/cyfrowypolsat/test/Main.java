package pl.cyfrowypolsat.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
		Calendar dateStart = Calendar.getInstance();
		List<String> dateRange = new LinkedList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		dateStart.set(2015, 0, 1);
		
//		System.out.println(dateStart.getTime());
//		System.out.println(new Date());
		
		while(dateStart.getTime().before(new Date())){
			dateRange.add(sdf.format(dateStart.getTime()));
			dateStart.add(Calendar.DATE, 1);
		}
		
		System.out.println(dateRange.size());
	}
	
	private void fileDownload(){
		HibernateUtil.createSessionFactory();
		
		FileDownloader fd = new FileDownloader();
		fd.run();
		
		HibernateUtil.getSessionFactory().close();
	}
}
