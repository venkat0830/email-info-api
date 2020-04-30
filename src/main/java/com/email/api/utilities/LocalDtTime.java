package com.email.api.utilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LocalDtTime {
	private static final String DATE = "yyyy-MM-dd HH:mm:ss";

	public static Date getLastUpdatedDate(String frequency) {
		TimeZone cstTimeZone = TimeZone.getTimeZone("CST6CDT");
		Calendar cal = Calendar.getInstance(cstTimeZone);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE, Locale.US);
		simpleDateFormat.setTimeZone(cstTimeZone);
		if (frequency.equals(Constants.FREQ_DAILY)) {
			cal.add(Calendar.DATE, -1);
			cal.add(Calendar.HOUR_OF_DAY, -(cal.get(Calendar.HOUR_OF_DAY)) + 6);
			cal.set(Calendar.MINUTE, -(cal.get(Calendar.MINUTE)) + 00);
			cal.set(Calendar.SECOND, -(cal.get(Calendar.SECOND)) + 00);

		}
		if (frequency.equals(Constants.FREQ_WEEKLY)) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			cal.add(Calendar.DATE, -7);
			cal.add(Calendar.HOUR_OF_DAY, -(cal.get(Calendar.HOUR_OF_DAY)) + 6);
			cal.set(Calendar.MINUTE, -(cal.get(Calendar.MINUTE)) + 00);
			cal.set(Calendar.SECOND, -(cal.get(Calendar.SECOND)) + 00);
		}

		Date dailyDateTime = cal.getTime();
//		String finalDate;
//		finalDate = simpleDateFormat.format(dailyDateTime);
		return dailyDateTime;
	}

	public static Date getCurrentDate() {
		TimeZone cstTimeZone = TimeZone.getTimeZone("CST6CDT");
		Calendar cal = Calendar.getInstance(cstTimeZone);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE, Locale.US);
		try {
			simpleDateFormat.setTimeZone(cstTimeZone);
			cal.set(Calendar.HOUR_OF_DAY, 5);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		} catch (Exception e) {
			throw e;
		}
		Date currentDateTime = cal.getTime();
		return currentDateTime;

	}

	public static Date getThisWeekMonday() {
		TimeZone cstTimeZone = TimeZone.getTimeZone("CST6CDT");
		Calendar cal = Calendar.getInstance(cstTimeZone);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE, Locale.US);
		try {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.HOUR_OF_DAY, 5);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		} catch (Exception e) {
			throw e;
		}
		Date currentDateTime = cal.getTime();
		return currentDateTime;

	}
	//Use this methods if needed
	public static boolean isValidUnixDate(String unixDateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(Long.valueOf(unixDateStr));
			String dateStr = sdf.format(date);
			Date currentDate = new Date();
			Timestamp ts = Timestamp.valueOf(dateStr);
			Timestamp ts1 = Timestamp.valueOf(sdf.format(currentDate));
			sdf.setLenient(false);
			if (ts.before(ts1)) {
				System.out.println("Test done");
				return true;
			}

		} catch (NumberFormatException ex) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	//Use this methods if needed
	public static String convertToDateStringUnixDate(String unixDateStr) {
		TimeZone cst = TimeZone.getTimeZone("CST6CDT");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
			sdf.setTimeZone(cst);
			Date date = new Date(Long.valueOf(unixDateStr));
			String dateStr = sdf.format(date);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date convertToDateUnixDate(String unixDateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(Long.valueOf(unixDateStr));
			String dateStr = sdf.format(date);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date convertDate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = sdf.format(date);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
