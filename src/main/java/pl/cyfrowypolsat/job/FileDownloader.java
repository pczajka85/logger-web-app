package pl.cyfrowypolsat.job;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import pl.cyfrowypolsat.dao.ApplicationDao;
import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.LogDir;
import pl.cyfrowypolsat.entity.LogfileExpression;

public class FileDownloader extends TimerTask {

	Logger log = Logger.getLogger("FileDownloader");
	
	public final static String MAIN_LOG_DIR = "/home/piotr/tmp/logs/";
	
	@Override
	public void run() {
		log.info("DOWNLOADING FILES STARTED...");
		for(Application app: ApplicationDao.getAll()){
			if(app.isStarted()){
				downloadFiles(app);
			}
		}
		log.info("DOWNLOADING FILES FINISHED...");
	}
	
	private void downloadFiles(Application app){
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(app.getHost());
			boolean login = ftpClient.login(app.getUsername(), app.getPassword());
			if(login){
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar yesterday = Calendar.getInstance();
				yesterday.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1);
				
				String yesterdayDateFormat = sdf.format(yesterday.getTime());
				List<File> addedFiles = new ArrayList<File>();
				for (LogDir remoteDir : app.getLogDirs()) {
					File dir = new File(
							MAIN_LOG_DIR + app.getName() + '/' + remoteDir.getPath()
							+ '/' + yesterdayDateFormat);
					if(!dir.exists()){
						dir.mkdirs();
					}
					for (FTPFile f : ftpClient.listFiles(remoteDir.getPath())) {
						if (f.getName().contains(yesterdayDateFormat)) {
							for (LogfileExpression lfe : app.getLogfileExpressions()) {
								if (f.getName().matches(lfe.getFileExpression())) {
									File df = new File(dir.getAbsolutePath() + '/' + f.getName());
									OutputStream os = new BufferedOutputStream(new FileOutputStream(df));
									boolean download = ftpClient.retrieveFile(
											remoteDir.getPath() + "/" + f.getName(), os);
									if (download) {
										log.info(f.getName() + " DOWNLOADED");
										addedFiles.add(df);
									}else{
										log.severe("Downloading " + f.getName() + " Error");
									}
									os.close();
								}
							}
						}
					}
				}
				ftpClient.logout();
				this.countAndSaveErrors(addedFiles);
			}else{
				log.severe("Login or password are incorrect for " + app.getName());
			}
		} catch (IOException e) {
			log.severe(e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//TODO implement it
	private void countAndSaveErrors(List<File> files) {
		for(File f : files){
			log.info("ADDED FILE="+f.getAbsolutePath());
		}
	}
}
