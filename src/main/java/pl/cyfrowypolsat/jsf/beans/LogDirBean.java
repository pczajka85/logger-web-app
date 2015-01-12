package pl.cyfrowypolsat.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pl.cyfrowypolsat.dao.LogDirDao;
import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.LogDir;

@ManagedBean
@SessionScoped
public class LogDirBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Application application;
	private String dirPath;
	private List<LogDir> logDirs = new ArrayList<LogDir>();
	
	public String startAction(Application app){
		this.application = app;
		logDirs = LogDirDao.getByApplication(application);
		return "logDir";
	}
	
	public String setEditableAction(LogDir ld){
		ld.setEditable(true);
		return null;
	}
	
	public String addAction(){
		LogDir ld = new LogDir();
		ld.setApplication(application);
		ld.setPath(dirPath);
		LogDirDao.save(ld);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Path has been added."));
		logDirs = LogDirDao.getByApplication(application);
		return null;
	}
	
	public String editAction(){
		for (LogDir ld : logDirs){
			if(ld.isEditable()){
				ld.setEditable(false);
				LogDirDao.save(ld);
			}
		}
		logDirs = LogDirDao.getByApplication(application);
		return null;
	}
	
	public String deleteAction(LogDir ld){
		LogDirDao.delete(ld);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Path has been removed."));
		logDirs = LogDirDao.getByApplication(application);
		return null;
	}
	
	public Application getApplication() {
		return application;
	}
	
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public String getDirPath() {
		return dirPath;
	}
	
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	
	public List<LogDir> getLogDirs() {
		return logDirs;
	}
	
	public void setLogDirs(List<LogDir> logDirs) {
		this.logDirs = logDirs;
	}
}
