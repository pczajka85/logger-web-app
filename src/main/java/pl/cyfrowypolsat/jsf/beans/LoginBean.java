package pl.cyfrowypolsat.jsf.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pl.cyfrowypolsat.dao.DeveloperDao;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean loggedIn = false;
	private String username;
	private String password;
	
	public String loginAction(){
		if(DeveloperDao.getOneByUsernameAndPassword(username, password) != null){
			loggedIn = true;
			return "/pages/index.xhtml?faces-redirect=true";
		}
		FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return "login.xhtml";
	}
	
	//TODO implement it
	public String logoutAction(){
		return null;
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

	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}

