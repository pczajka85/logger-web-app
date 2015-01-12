package pl.cyfrowypolsat.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;

@Entity
public class Application implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private long id;
	
	private String name;
	
	private String host;
	
	private String username;
	
	private String password;
	
	private boolean started;

	@OneToMany(mappedBy="application", fetch=FetchType.EAGER)
	@Cascade(CascadeType.DELETE)
	private Set<LogDir> logDirs;
	
	@OneToMany(mappedBy="application", fetch=FetchType.EAGER)
	@Cascade(CascadeType.DELETE)
	private Set<LogfileExpression> logfileExpressions;
	
	@Transient
	private boolean editable;
	
	public long getId() {
		return id;
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
	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public Set<LogDir> getLogDirs() {
		return logDirs;
	}

	public void setLogDirs(Set<LogDir> logDirs) {
		this.logDirs = logDirs;
	}

	public Set<LogfileExpression> getLogfileExpressions() {
		return logfileExpressions;
	}

	public void setLogfileExpressions(Set<LogfileExpression> logfileExpressions) {
		this.logfileExpressions = logfileExpressions;
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public void setStarted(boolean started) {
		this.started = started;
	}

}
