package pl.cyfrowypolsat.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
//		String exc1 = "INFO: HHH000424: Disabling contextual LOB creation as createClob() method threw error : java.lang.reflect.InvocationTargetException";
//		String exc2 = "org.postgresql.util.PSQLException: Połączenie odrzucone. Sprawdź, czy prawidłowo ustawiłeś nazwę hosta oraz port i upewnij się, czy postmaster przyjmuje połączenia TCP/IP.";
//		String exc3 = "2014-12-06 13:49:21,794 WARN  [pl.cyfrowypolsat.homepage.filter.TvProgramRequestsMapperFilter] (ajp-10.217.83.110-8009-42) Parsowanie id eventu, requestURI=/program-tv/ginx-tv/textLayout_2.0.0.232.swz java.lang.NumberFormatException: For input string: ";
//		
//		String rule = "^[a-zA-z\\.]+Exception: .*";
//		
//		System.out.println(exc1.matches(rule));
//		System.out.println(exc2.matches(rule));
//		System.out.println(exc3.matches(rule));
		Main m = new Main();
		m.fileDownload();
	}
	
	private void fileDownload(){
		HibernateUtil.createSessionFactory();
		
		FileDownloader fd = new FileDownloader();
		fd.run();
		
		HibernateUtil.getSessionFactory().close();
	}
}
