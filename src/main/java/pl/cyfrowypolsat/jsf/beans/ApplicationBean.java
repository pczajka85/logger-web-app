package pl.cyfrowypolsat.jsf.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pl.cyfrowypolsat.dao.ApplicationDao;
import pl.cyfrowypolsat.entity.Application;

@ManagedBean
@SessionScoped
public class ApplicationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String host;
	private String username;
	private String password;
	private List<Application> applications = ApplicationDao.getAll();
	
	public String addAction(){
		Application application = new Application();
		application.setHost(host);
		application.setName(name);
		application.setPassword(password);
		application.setUsername(username);
		ApplicationDao.save(application);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aplikacja dodana pomyslnie."));
		this.applications = ApplicationDao.getAll();
		return null;
	}
	
	public String setEditableAction(Application app){
		app.setEditable(true);
		return null;
	}
	
	public String editAction(){
		for(Application a : applications){
			if(a.isEditable()){
				ApplicationDao.save(a);
				a.setEditable(false);
			}
		}
		this.applications = ApplicationDao.getAll();
		return null;
	}
	
	public String deleteAction(Application app){
		ApplicationDao.delete(app);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aplikacja usunieta pomyslnie."));
		this.applications = ApplicationDao.getAll();
		return null;
	}
	
	public String refreshAction(){
		this.applications = ApplicationDao.getAll();
		return "application?faces-redirect=true";
	}
	
	public String startAction(Application app){
		ApplicationDao.setStarted(app, true);
		return null;
	}
	
	public String stopAction(Application app){
		ApplicationDao.setStarted(app, false);
		return null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
}
