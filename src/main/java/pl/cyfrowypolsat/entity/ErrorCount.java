package pl.cyfrowypolsat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ErrorCount {

	@Id @GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Application application;
	
	@Column(nullable=false)
	private String errorType;
	
	@Column(nullable=false)
	private Date date;
	
	@Column(nullable=false)
	private int count;

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
