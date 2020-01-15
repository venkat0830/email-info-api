//package com.email.api;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import com.email.api.utilities.Constants;
//
//public class Test {
//
//	private static final String DATE = "yyyy-MM-dd HH:mm:ss";
//	private static final String SMARTEDIT_DATE = "MM/dd/yyyy";`
//
//	public static void main(String[] args) throws Exception {

		/**********************************************************************/
		////////////////////// For Previous Dates //////////////////////////
		/***************************************************************************/

		//
		// TimeZone timeZone = TimeZone.getTimeZone("CST6CDT");
		// Calendar cal = Calendar.getInstance(timeZone);
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE, Locale.US);
		// simpleDateFormat.setTimeZone(timeZone);
		//
		// //cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// cal.add(Calendar.DATE, -1);
		// cal.add(Calendar.HOUR_OF_DAY, -(cal.get(Calendar.HOUR_OF_DAY)) +6);
		// cal.add(Calendar.MINUTE, -(cal.get(Calendar.MINUTE)) + 00);
		// cal.add(Calendar.SECOND, -(cal.get(Calendar.SECOND)) + 00);
		// cal.setTimeZone(TimeZone.getTimeZone("CST6CDT"));
		// Date date1 = cal.getTime();
		//
		//
		// System.out.println("Previous Date====================>>>> : "
		// +simpleDateFormat.format(date1));
		/**********************************************************************/
		/* ===========================For GMT and CST ========================== */
		/***************************************************************************/
		// Date date = new Date();
		// DateFormat cstFormat = new SimpleDateFormat();
		// DateFormat gmtFormat = new SimpleDateFormat();
		// TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		// TimeZone cstTime = TimeZone.getTimeZone("CST6CDT");
		//
		// cstFormat.setTimeZone(gmtTime);
		// gmtFormat.setTimeZone(cstTime);
		// Date currentDate = cstFormat.parse(cstFormat.format(date));
		// System.out.println("GMT Time=======================>>>>> : " +
		// cstFormat.format(currentDate));//
		// System.out.println("CST Time=======================>>>>> : " +
		// gmtFormat.format(date));
		//
		/**********************************************************************/
		////////////////////// For Current Dates //////////////////////////
		/***************************************************************************/
		// SimpleDateFormat s1 = new SimpleDateFormat(DATE);
		// String dateInString = getCDT();
		// Date date = new Date();
		// date = s1.parse(dateInString);
		// Calendar c2 = dateToCalendar(date);
		// //c2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// c2.set(Calendar.HOUR_OF_DAY, 5);
		// c2.set(Calendar.MINUTE, 59);
		// c2.set(Calendar.SECOND, 59);
		// Date currentdate = c2.getTime();
		// s1.setTimeZone(TimeZone.getTimeZone("CST"));
		// System.out.println("Date Upto =========================>>>>> : " +
		// s1.format(currentdate));
		//////////////////////// Daily/////////////////////
		//
//		TimeZone timeZone = TimeZone.getTimeZone("CST6CDT");
//		Calendar cal = Calendar.getInstance(timeZone);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE, Locale.US);
//		simpleDateFormat.setTimeZone(timeZone);
//		try {
//			//cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//			cal.set(Calendar.HOUR_OF_DAY, 5);
//			cal.set(Calendar.MINUTE, 59);
//			cal.set(Calendar.SECOND, 59);
//		} catch (Exception e) {
//			throw e;
//		}
//		Date currentDateTime = cal.getTime();
//		System.out.println("CurrentDate =====================>>> : " + simpleDateFormat.format(currentDateTime));
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SMARTEDIT_DATE);
//		int lastDate;
//		TimeZone etTimeZone = TimeZone.getTimeZone("America/Chicago");
//		simpleDateFormat.setTimeZone(etTimeZone);
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeZone(etTimeZone);
//		cal.add(Calendar.DATE, -lastDate);
//		System.out.println("Date:" + cal);
//	}
//
//}
