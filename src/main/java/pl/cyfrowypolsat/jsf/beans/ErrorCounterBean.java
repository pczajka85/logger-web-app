package pl.cyfrowypolsat.jsf.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	public String refreshAction(){
		date = new Date();
		applications = ApplicationDao.getByErrorCounterDate(date);
		return "errorCounter?faces-redirect=true";
	}
	
	public List<Application> getApplications() {
		return applications;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String setDateAction(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		try {
			this.date = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		applications = ApplicationDao.getByErrorCounterDate(this.date);
		
		return null;
	}
}
