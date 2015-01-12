package pl.cyfrowypolsat.listener;

import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import pl.cyfrowypolsat.job.FileDownloader;
import pl.cyfrowypolsat.util.HibernateUtil;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextListener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
        //building session factory
        HibernateUtil.createSessionFactory();
        
        //FileDownloader cron
        //start from next day 1 minute after midnight
        Timer timer = new Timer();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH)+1);
		date.set(Calendar.HOUR, 0);
		date.set(Calendar.MINUTE, 1);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		System.out.println("I will start at "+date.getTime());
		timer.schedule(new FileDownloader(), date.getTime(), 1000*60*60*24);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  {
    	HibernateUtil.getSessionFactory().close();
    }
	
}
