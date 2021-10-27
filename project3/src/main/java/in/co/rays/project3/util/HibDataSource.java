package in.co.rays.project3.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



/**
 * Hibernate DataSource is provides the object of sessionfactory and session
 * @author Yash Pandey
 *
 */
public class HibDataSource {
	
	private static SessionFactory sf = null ; 
	
	public static SessionFactory getSessionFactory(){
		if(sf == null){
			sf = new Configuration().configure().buildSessionFactory();
		}
		
		return sf;
		
	}
		
	
	public static Session getSession(){
		Session s = getSessionFactory().openSession();
		return s;
	}
	
	
	public static void closeSession(Session session){
		if(session==null){
			session.close();
		}
	}
		
		
}

