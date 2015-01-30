package pl.cyfrowypolsat.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LogfileExpression implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private long id ;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Application application;
	
	@Column(nullable=false)
	private String fileExpression;

	private boolean editable;
	
	public Application getApplication() {
		return application;
	}
	
	public void setApplication(Application application) {
		this.application = application;
		application.addLogFileExpression(this);
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
	
	public long getId() {
		return id;
	}
}
