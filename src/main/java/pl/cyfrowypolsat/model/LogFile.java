package pl.cyfrowypolsat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.cyfrowypolsat.jsf.beans.Logs;

public class LogFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String path;
	private List<SmallContent> smallContent = new ArrayList<LogFile.SmallContent>();
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<SmallContent> getSmallContent() {
		return smallContent;
	}
	
	public void setSmallContent(List<SmallContent> smallContent) {
		this.smallContent = smallContent;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public void addSmallContent(String content, int line) {
		SmallContent sc = new SmallContent();
		sc.setContent(content);
		sc.setStartLine(line - Logs.moreLinesNo - 1);
		sc.setEndLine(line);
		sc.setLogfile(this);
		smallContent.add(sc);
	}
	
	public class SmallContent implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		private LogFile logfile;
		private String content;
		private int startLine;
		private int endLine;
		
		public String getContent() {
			return content;
		}
		
		public void setContent(String content) {
			this.content = content;
		}
		
		public LogFile getLogfile() {
			return logfile;
		}
		
		public void setLogfile(LogFile logfile) {
			this.logfile = logfile;
		}

		public int getStartLine() {
			return startLine;
		}

		public void setStartLine(int startLine) {
			this.startLine = startLine;
		}

		public int getEndLine() {
			return endLine;
		}

		public void setEndLine(int endLine) {
			this.endLine = endLine;
		}
	}
}