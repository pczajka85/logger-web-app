package pl.cyfrowypolsat.test;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;

import pl.cyfrowypolsat.dao.ApplicationDao;
import pl.cyfrowypolsat.dao.DeveloperDao;
import pl.cyfrowypolsat.dao.ErrorCounterDao;
import pl.cyfrowypolsat.dao.LogDirDao;
import pl.cyfrowypolsat.dao.LogFileExpressionDao;
import pl.cyfrowypolsat.entity.Application;
import pl.cyfrowypolsat.entity.Developer;
import pl.cyfrowypolsat.entity.ErrorCount;
import pl.cyfrowypolsat.entity.LogDir;
import pl.cyfrowypolsat.entity.LogfileExpression;
import pl.cyfrowypolsat.job.FileDownloader;

public class AllTests extends Assert {

	@Test
	public void systemConfiguration(){
		File dir = new File(FileDownloader.MAIN_LOG_DIR);
		assertTrue(dir.exists());
	}
	
	@Test
	public void loginWithMd5() {
		String pass = "xxx";		
		assertEquals(DeveloperDao.md5(pass), "f561aaf6ef0bf14d4208bb46a4ccb3ad");
		
		for(Developer d : DeveloperDao.getAllDevelopers()){
			assertEquals(d.getPassword().length(), 32);
		}
	}
	
	@Test
	public void applicationCrud() {
		Application app = getTestApp();
		
		Set<LogfileExpression> lfeList = new HashSet<LogfileExpression>();
		LogfileExpression lfe1 = new LogfileExpression();
		lfe1.setEditable(true);
		lfe1.setFileExpression("(*)icok(*)");
		lfeList.add(lfe1);
		LogfileExpression lfe2 = new LogfileExpression();
		lfe2.setEditable(true);
		lfe2.setFileExpression("(*)icok(*)");
		lfeList.add(lfe2);
		app.setLogfileExpressions(lfeList);
		
		Set<LogDir> ldSet = new HashSet<LogDir>();
		LogDir ld1 = new LogDir();
		ld1.setEditable(true);
		ld1.setPath("logs");
		ldSet.add(ld1);
		LogDir ld2 = new LogDir();
		ld2.setEditable(true);
		ld2.setPath("logs");
		ldSet.add(ld2);
		app.setLogDirs(ldSet);
		
		//create
		ApplicationDao.save(app);
		//read
		Application appDao = ApplicationDao.find(app.getId());
		assertTrue(app.getId() == appDao.getId());
		//update
		app.setEditable(false);
		ApplicationDao.save(app);
		//delete
		try{
			ApplicationDao.delete(app);
		}catch(Exception e){
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void logFileExpressionCrud() {
		Application app = getTestApp();
		ApplicationDao.save(app);
		
		LogfileExpression lfe3 = new LogfileExpression();
		lfe3.setEditable(true);
		lfe3.setFileExpression("(*)icok(*)");
		lfe3.setApplication(app);
		//create
		LogFileExpressionDao.save(lfe3);
		//read
		LogfileExpression lfeDao = LogFileExpressionDao.getByApp(app).get(0);
		assertTrue(lfeDao.getId() == lfe3.getId());
		//update
		lfe3.setEditable(false);
		LogFileExpressionDao.save(lfe3);
		
		//delete
//		LogFileExpressionDao.delete(lfe3);
		
		try{
			ApplicationDao.delete(app);
		}catch(ConstraintViolationException e){
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void logDirCrud() {
		Application app = getTestApp();
		ApplicationDao.save(app);
		
		LogDir ld = new LogDir();
		ld.setApplication(app);
		ld.setEditable(true);
		ld.setPath("logs");
		
		LogDirDao.save(ld);
		
		ld.setEditable(false);
		LogDirDao.save(ld);
		
		LogDir ldDao = LogDirDao.getByApplication(app).get(0);
		assertEquals(ld.getId(), ldDao.getId());
		
		try{
			ApplicationDao.delete(app);
		}catch(ConstraintViolationException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void errorCountCrud() {
		Application application = getTestApp();
		ErrorCount ec = new ErrorCount();
		ec.setApplication(application);
		ec.setDate(new Date());
		ec.setCount(100);
		ec.setErrorType("NullPointerException");
		ErrorCounterDao.save(ec);
		
		ApplicationDao.delete(application);
	}
		
	private Application getTestApp() {
		Application app = new Application();
		app.setEditable(true);
		app.setHost("ftp.icok.pl");
		app.setName("ICOK");
		app.setPassword("pass");
		app.setStarted(true);
		app.setUsername("tester1");
		return app;
	}
}
