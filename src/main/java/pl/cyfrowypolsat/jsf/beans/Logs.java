package pl.cyfrowypolsat.jsf.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="logs")
@SessionScoped
public class Logs implements Serializable {

	private static final long serialVersionUID = 1L;

	private String searchingPhrase;
	private Date date;
	private List<LogFile> logFiles = new ArrayList<Logs.LogFile>();
	
	public String searchAction(){
		logFiles = new ArrayList<Logs.LogFile>();
		try{
			///TODO check srv/ftp/logs_examples/server.log.2014-12-06.gz
			File file = new File("/home/piotr/Documents/Cyfrowy_Polsat/logs_examples/icok2.2014-12-16.log.zip");
			FileInputStream fileStream = new FileInputStream(file);
			ZipInputStream zipInput = new ZipInputStream(fileStream);
			ZipEntry entry;
			while((entry = zipInput.getNextEntry()) != null){
				BufferedReader in = new BufferedReader(new InputStreamReader(zipInput));
				String s;
				LogFile logFile = new LogFile();
				boolean getMoreStackTrace = false;
				int stackTraceLineNo = 0;
				StringBuffer sb = new StringBuffer();
				int i  = 1; // line numbers
				while((s = in.readLine()) != null/*& i < 1000*/){
					if(s.contains(searchingPhrase)){
						logFile.setFileName(file.getName());
						sb.append("line: " + i + "<br>");
						getMoreStackTrace = true;
					}
					if(getMoreStackTrace == true && stackTraceLineNo < 6){
						sb.append(s+"<br>");
						stackTraceLineNo++;
					}
					if(stackTraceLineNo == 5){
						logFile.addSmallContent(sb.toString());
						sb = new StringBuffer();
						stackTraceLineNo = 0;
						getMoreStackTrace = false;
					}
					i++;
				}
				logFiles.add(logFile);
				zipInput.closeEntry();
			}
			zipInput.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getSearchingPhrase() {
		return searchingPhrase;
	}
	
	public void setSearchingPhrase(String searchingPhrase) {
		this.searchingPhrase = searchingPhrase;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public List<LogFile> getLogFiles() {
		return logFiles;
	}
	
	public class LogFile implements Serializable{
		private static final long serialVersionUID = 1L;
		String fileName;
		List<String> smallContent = new ArrayList<String>();
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public List<String> getSmallContent() {
			return smallContent;
		}
		public void setSmallContent(List<String> smallContent) {
			this.smallContent = smallContent;
		}
		public void addSmallContent(String content){
			smallContent.add(content);
		}
	}
	
}
