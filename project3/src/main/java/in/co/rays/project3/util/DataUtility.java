package in.co.rays.project3.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/** 
 *  The Class DataUtility.
 *  
 * data Uility class to formate data
 * 
 * @author Yash Pandey
 *
 */
public class DataUtility {

	/**
	 * Application time data formate
	 */
	public static final String APP_DATE_FORMATE="MM/dd/yyyy";
	
	public static final String APP_TIME_FORMATE="MM/dd/yyyy HH:mm:ss";
	
	/**
	 * Applicaton time data formate
	 */
	public static final SimpleDateFormat formatter=new SimpleDateFormat(APP_DATE_FORMATE);
	
	private static final SimpleDateFormat timeFormatter=new SimpleDateFormat(APP_TIME_FORMATE);
	
	
	/**
	 * getString(String s) Trims and trailing and leading spaces of a String
	 * 
	 * @param val
	 * @return val
	 */
	public static String getString(String val){
		System.out.println("getString() of DU");
		if(DataValidator.isNotNull(val)){
			return val.trim();
		}else{
			return val;
		}
	}
	
	/**
	 * Converts and Object to String
	 * 
	 * @param val
	 *            :value
	 * @return String
	 */
	public static String getStringData(Object val){
		System.out.println("getStringData() of DU");
		if (val!=null){
			return val.toString();
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * Converts String InTo Integer
	 * 
	 * @param val
	 *            :value
	 * @return int
	 */
	public static int getInt(String val){
		System.out.println("getInt() of DU");
		if (DataValidator.isLong(val)){
			return Integer.parseInt(val);
		}else{
			return 0;
		}
	}
	
	/**
	 * 
	 * Converts String InTo Long
	 * 
	 * @param val
	 *            :value
	 * @return Long
	 */
	 public static long getLong(String val) {
		 System.out.println("getLong() of DU");
	        if (DataValidator.isLong(val)) {
	            return Long.parseLong(val);
	        } else {
	            return (long) 0;
	        }
	    }
	 
	 /**
		 * Convert String in to Date
		 * 
		 * @param val
		 *            :value
		 * @return Date
		 */
	  public static Date getDate(String val) {
		  System.out.println("getDate() of DU");
	        Date date = null;
	        try {
	            date = formatter.parse(val);
	        } catch (Exception e) {

	        }
	        return date;
	    }
	  
	  /**
		 * convert string to date
		 * @param date
		 * @return
		 */
	  public static String getDateString(Date date) {
		  System.out.println("getDateString() of DU");
	        try {
	            return formatter.format(date);
	        } catch (Exception e) {
	        }
	        return "";
	    }
	  
	  /**
		 * convert date and time
		 * @param date * 	
		 *  * @param day
		 * @return
		 */
	  public static Date getDate(Date date, int day){
		  System.out.println("getDate() of DU");
		  return null;
	  }
	  
	  /**
		 * convert timestamp to string
		 * @param val
		 * @return timestamp
		 */
	  public static Timestamp getTimestamp(String val) {

	        Timestamp ts = null;
	        try {
	            ts = new Timestamp((timeFormatter.parse(val)).getTime());
	        } catch (Exception e) {
	            return null;
	        }
	        return ts;
	    }
	  
	  /** 
		 * convert timestamp in to long
		 * @param l
		 * @return timestamp
		 */
	  public static Timestamp getTimestamp(long l) {
		  System.out.println("getTimestamp() of DU");

	        Timestamp ts = null;
	        try {
	            ts = new Timestamp(l);
	        } catch (Exception e) {
	            return null;
	        }
	        return ts;
	    }
	  
	  /**
		 * convert timestamp in to string
		 * @return timestamp
		 */
	  public static Timestamp getCurrentTimestamp() {
		  System.out.println("getCurrentTimestamp() of DU");
	        Timestamp ts = null;
	        try {
	            ts = new Timestamp(new Date().getTime());
	        } catch (Exception e) {
	        }
	        return ts;

	    }
	  
	  /**
		 * convert timestamp timestamp to long
		 * @param tm
		 * @return
		 */
	  public static long getTimestamp(Timestamp t) {
		  System.out.println(" long getTimestamp() of DU");
	        try {
	            return t.getTime();
	        } catch (Exception e) {
	            return 0;
	        }
	    }

	    public static void main(String[] args) {
	        System.out.println("DU(main)"+getInt("124"));
	    }
	    
	    
}