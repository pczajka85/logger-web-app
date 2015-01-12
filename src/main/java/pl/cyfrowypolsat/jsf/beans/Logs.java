package pl.cyfrowypolsat.jsf.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import pl.cyfrowypolsat.dao.ApplicationDao;
import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.LogDir;
import pl.cyfrowypolsat.job.FileDownloader;
import pl.cyfrowypolsat.model.LogFile;
import pl.cyfrowypolsat.model.LogFile.SmallContent;
import pl.cyfrowypolsat.tools.SearchPhrase;

@ManagedBean(name="logs")
@SessionScoped
public class Logs implements Serializable {

	private static final long serialVersionUID = 1L;
	private int filesNo;
	private static final Logger logger = Logger.getLogger("Logs");
	public static final int moreLinesNo = 5;

	private String searchingPhrase;
	private Date date;
	private List<LogFile> logFiles = new ArrayList<LogFile>();
	private String message;
	
	private Long applicationId;
	private Map<String, Object> applicationsValue = new HashMap<String, Object>();
	
	public Logs() {
		for(Application app : ApplicationDao.getAll()){
			applicationsValue.put(app.getName(), app.getId());
		}
	}

	public String searchAction() throws IOException {
		logFiles = new ArrayList<LogFile>();
		this.filesNo = 0;
		Application app = ApplicationDao.find(this.applicationId);
		String dir = FileDownloader.MAIN_LOG_DIR;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<File> folders = new ArrayList<File>();
		for(LogDir ld : app.getLogDirs()){
			File folder = new File(dir+app.getName()+'/'+ld.getPath()+'/'+sdf.format(date));
			this.filesNo += folder.listFiles().length;
			folders.add(folder);
		}
		logger.info("FILES NO = " + this.filesNo);
		for(File folder : folders){
			Thread[] threads = new Thread[folder.listFiles().length];
			int i = 0;
			for (File file : folder.listFiles()) {
				logger.info("FILE = "+file.getAbsolutePath());
				threads[i] = new Thread(new SearchPhrase(file, searchingPhrase));
				threads[i].start();
				logger.info("Thread " + i + " started.");
				i++;
			}
			checkIfResultIsReady();
			logger.info("folder = " + folder.toString());
		}
		if (this.filesNo == 0){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Nie znaleziono logow dla tej aplikacji w podanej dacie"));
		}
		return null;
	}
		
	public void checkIfResultIsReady(){
		try {
			logger.info("Checking... Result is not ready yet.");
			if(logFiles.size() < this.filesNo){
				Thread.sleep(2000);
				checkIfResultIsReady();
			}else{
				logger.info("Finished!");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String showMoreAboveAction(SmallContent sc){
		logger.info("sc=" + sc);
		int startLine = sc.getStartLine()-moreLinesNo;
		if(sc.getStartLine() <= moreLinesNo){
			startLine = 1;
		}
		int endLine = sc.getStartLine();
		String moreContent = this.showLines(sc.getLogfile().getPath(), startLine, endLine);
		sc.setContent(moreContent + sc.getContent());
		sc.setStartLine(startLine);
		return null;
	}
	
	public String showMoreBelowAction(SmallContent sc){
		int startLine = sc.getEndLine();
		int endLine = sc.getEndLine()+moreLinesNo;
		String moreContent = this.showLines(sc.getLogfile().getPath(), startLine, endLine);
		sc.setContent(sc.getContent() + moreContent);
		sc.setEndLine(endLine);
		return null;
	}
	
	private String showLines(String fileName, int startLine, int endLine)  {
		String line = null;
		int currentLineNo = 0;

		BufferedReader in = null;
		try {
			in = new BufferedReader (new FileReader(fileName));
			
			//read to startLine
			while(currentLineNo<startLine) {
				if (in.readLine()==null) {
					// oops, early end of file
					throw new IOException("File too small");
				}
				currentLineNo++;
			}
			
			//read until endLine
			while(currentLineNo<=endLine) {
				line += in.readLine()+"<br/>";
				currentLineNo++;
			}
			return line;
			
		} catch (IOException ex) {
			System.out.println("Problem reading file.\n" + ex.getMessage());
		} finally {
			try { if (in!=null) in.close(); } catch(IOException ignore) {}
		}
		return null;
	}
	
	protected void refreshPage() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String refreshpage = fc.getViewRoot().getViewId();
		ViewHandler ViewH =fc.getApplication().getViewHandler();
		UIViewRoot UIV = ViewH.createView(fc,refreshpage);
		UIV.setViewId(refreshpage);
		fc.setViewRoot(UIV);
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
	
	public void addLogFile(LogFile lf){
		logFiles.add(lf);
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Map<String, Object> getApplicationsValue() {
		return applicationsValue;
	}
	
	public void setApplicationsValue(Map<String, Object> applicationsValue) {
		this.applicationsValue = applicationsValue;
	}
	
	public Long getApplicationId() {
		return applicationId;
	}
	
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
}
