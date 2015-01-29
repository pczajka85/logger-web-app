package pl.cyfrowypolsat.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.naming.InvalidNameException;

import pl.cyfrowypolsat.jsf.beans.Logs;
import pl.cyfrowypolsat.model.LogFile;

public class SearchPhrase implements Runnable {

	private static final Logger logger = Logger.getLogger("SearchPhrase");
	
	private File file;
	private String searchingPhrase;

	private Logs logsBean;
	private FacesContext context;

	public SearchPhrase(File f, String sp) {
		this.setFile(f);
		this.setSearchingPhrase(sp);
		context = FacesContext.getCurrentInstance();
		logsBean = context.getApplication().evaluateExpressionGet(context, "#{logs}", Logs.class);
	}

	@Override
	public void run() {
		if (!new File(Unpacker.getUnpackedFileName(file)).exists()) {
			Unpacker unpacker = new Unpacker();
			try {
				File packedFile = file;
				file = unpacker.unpack(file);
				packedFile.delete();
			} catch (InvalidNameException | IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			LogFile logFile = new LogFile();
			logFile.setPath(file.getAbsolutePath());
			logFile.setFileName(file.getName());
			StringBuffer sb = new StringBuffer();
			boolean getMoreStackTrace = false;
			int stackTraceLineNo = 0;
			String s;
			int i = 1; // line numbers
			while ((s = in.readLine()) != null) {
				if (s.contains(searchingPhrase)) {
					getMoreStackTrace = true;
				}
				if (getMoreStackTrace == true && stackTraceLineNo < 6) {
					sb.append(getFormattedText(s, searchingPhrase));
					stackTraceLineNo++;
				}
				if (stackTraceLineNo == 5) {
					logFile.addSmallContent(sb.toString(), i);
					sb = new StringBuffer();
					stackTraceLineNo = 0;
					getMoreStackTrace = false;
				}
				i++;
			}
			logsBean.addLogFile(logFile);
			in.close();
			logger.info("One of log scanning ended.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static String getFormattedText(String text, String searchingPhrase){
		return text.replaceAll("\t", "&emsp;")
				.replaceAll(searchingPhrase, "<b>"+searchingPhrase+"</b>") 
				+ "<br>";
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getSearchingPhrase() {
		return searchingPhrase;
	}

	public void setSearchingPhrase(String searchingPhrase) {
		this.searchingPhrase = searchingPhrase;
	}

}
