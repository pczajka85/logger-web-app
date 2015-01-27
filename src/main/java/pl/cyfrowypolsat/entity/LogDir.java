package pl.cyfrowypolsat.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class LogDir implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private long id;
	
	private String path;
	
	@Transient
	private boolean editable;
	
	@ManyToOne
	@JoinColumn(name="application_id")
	private Application application;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
		application.addLogDir(this);
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
