package pl.cyfrowypolsat.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LogfileExpression implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private long id ;
	
	@ManyToOne
	private Application application;
	
	private String fileExpression;

	private boolean editable;
	
	public Application getApplication() {
		return application;
	}
	
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public String getFileExpression() {
		return fileExpression;
	}

	public void setFileExpression(String fileExpression) {
		this.fileExpression = fileExpression;
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
