package pl.cyfrowypolsat.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		File file = new File("/home/piotr/tmp/logs/ICOK/logs/2015-01-27/catalina.2015-01-27.log");
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String s;
			int i = 0;
			boolean jedziesz = false;
			while((s = in.readLine()) != null){
//				System.out.println("TAB="+s.contains("\t"));
				String ns = s.replaceAll("\n", "<br>");
				if(s.contains("NumberFormatException: For input string:")){
					System.out.println(ns);
					jedziesz = true;
				}
				if(jedziesz){
					System.out.println(ns);
					i++;
				}
				if(i > 10){
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Main m = new Main();
//		m.fileDownload();
	}
	
	private void fileDownload(){
		HibernateUtil.createSessionFactory();
		
		FileDownloader fd = new FileDownloader();
		fd.run();
		
		HibernateUtil.getSessionFactory().close();
	}
}
