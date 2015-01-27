package pl.cyfrowypolsat.jsf.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pl.cyfrowypolsat.dao.ApplicationDao;
import pl.cyfrowypolsat.entity.Application;

@ManagedBean
@SessionScoped
public class ErrorCounterBean {

	private List<Application> applications;
	private Date date;
	private List<String> dateRange = new LinkedList<String>();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	public String refreshAction(){
		Calendar dateStart = Calendar.getInstance();
		dateStart.set(2015, 0, 1);
		if (dateRange.size() == 0) {
			while (dateStart.getTime().before(new Date())) {
				dateRange.add(sdf.format(dateStart.getTime()));
				dateStart.add(Calendar.DATE, 1);
			}
		}
		this.date = new Date();
		this.applications = ApplicationDao.getByErrorCounterDate(this.date);
		return "errorCounter?faces-redirect=true";
	}
	
	public List<Application> getApplications() {
		return applications;
	}
	
	public Date getDate() {
		return date;
	}
	
	public List<String> getDateRange() {
		return dateRange;
	}
	
	public String setDateAction(String date) {
		try {
			this.date = this.sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		applications = ApplicationDao.getByErrorCounterDate(this.date);
		
		return null;
	}
}
