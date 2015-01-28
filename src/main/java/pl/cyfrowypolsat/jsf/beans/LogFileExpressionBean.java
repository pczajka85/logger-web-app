package pl.cyfrowypolsat.jsf.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pl.cyfrowypolsat.dao.LogFileExpressionDao;
import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.LogfileExpression;

@ManagedBean
@SessionScoped
public class LogFileExpressionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String expression;
	private Application app;
	private List<LogfileExpression> logFileExpressions;
	
	public String startAction(Application app){
		this.app = app;
		this.logFileExpressions = LogFileExpressionDao.getByApp(app);
		return "logFileExpression";
	}
	
	public String addAction(){
		LogfileExpression lfe = new LogfileExpression();
		lfe.setApplication(app);
		lfe.setFileExpression(expression);
		LogFileExpressionDao.save(lfe);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expression has been added"));
		this.logFileExpressions = LogFileExpressionDao.getByApp(this.app);
		return null;
	}
	
	public String deleteAction(LogfileExpression lfe){
		LogFileExpressionDao.delete(lfe);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Expression has been removed"));
		this.logFileExpressions = LogFileExpressionDao.getByApp(this.app);
		return null;
	}
	
	public String editAction(){
		for(LogfileExpression lfe : logFileExpressions){
			if(lfe.isEditable()){
				lfe.setEditable(false);
				LogFileExpressionDao.save(lfe);
			}
		}
		return null;
	}
	
	public String setEditableAction(LogfileExpression lfe){
		lfe.setEditable(true);
		return null;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Application getApp() {
		return app;
	}
	
	public void setApp(Application app) {
		this.app = app;
	}
	
	public List<LogfileExpression> getLogFileExpressions() {
		return logFileExpressions;
	}

	public void setLogFileExpressions(List<LogfileExpression> logFileExpressions) {
		this.logFileExpressions = logFileExpressions;
	}
	
}
