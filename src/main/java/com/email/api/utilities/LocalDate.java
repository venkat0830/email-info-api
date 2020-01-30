package com.email.api.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;

public class LocalDate {

	private static final String DATE = "yyyy-MM-dd HH:mm:ss";
	private static final String SMARTEDIT_DATE = "MM/dd/yyyy";

	public static String getCDT() {
		SimpleDateFormat cst = new SimpleDateFormat(DATE);
		cst.setTimeZone(TimeZone.getTimeZone("CST6CDT"));
		return cst.format(new Date());
	}

	public static Date getFromatedDate(String lastDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE);
		Date formatDate = new Date();
		try {
			formatDate = simpleDateFormat.parse(lastDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate;
	}

	public static boolean isMondayToday() {
		Calendar cal = Calendar.getInstance();
		//cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		if (Calendar.WEDNESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			return true;
		}
		return false;

	}

	public static Date getLastUpdatedDate(String frequency) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE);
		Calendar cal = Calendar.getInstance();
		if (frequency.equals(Constants.FREQ_DAILY)) {
			cal.add(Calendar.DATE, -1);
			cal.add(Calendar.HOUR_OF_DAY, -(cal.get(Calendar.HOUR_OF_DAY) - 5));
			cal.set(Calendar.MINUTE, -(cal.get(Calendar.MINUTE) - 59));
			cal.set(Calendar.SECOND, -(cal.get(Calendar.SECOND) - 59));

		}
		if (frequency.equals(Constants.FREQ_WEEKLY)) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			cal.add(Calendar.DATE, -7);
			cal.add(Calendar.HOUR_OF_DAY, -(cal.get(Calendar.HOUR_OF_DAY) - 5));
			cal.set(Calendar.MINUTE, -(cal.get(Calendar.MINUTE) - 59));
			cal.set(Calendar.SECOND, -(cal.get(Calendar.SECOND) - 59));
		}

		Date dailyDateTime = cal.getTime();
		Date formatDate = new Date();
		try {
			formatDate = simpleDateFormat.parse(simpleDateFormat.format(dailyDateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate;
	}

	public static Date getCurrentDate() { //// Daily
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE);
		try {
			cal.set(Calendar.HOUR_OF_DAY, 6);
			cal.set(Calendar.MINUTE, 00);
			cal.set(Calendar.SECOND, 00);
		} catch (Exception e) {
			throw e;
		}
		Date currentDateTime = cal.getTime();
		return currentDateTime;

	}

	public static String getThisWeekMonday() { //// Weekly
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE);
		try {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			cal.set(Calendar.HOUR_OF_DAY, 6);
			cal.set(Calendar.MINUTE, 00);
			cal.set(Calendar.SECOND, 00);
		} catch (Exception e) {
			throw e;
		}
		Date currentDateTime = cal.getTime();
		return simpleDateFormat.format(currentDateTime);

	}

//	public static Date getLastUpdatedDate(int lastDate) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SMARTEDIT_DATE);
//		TimeZone etTimeZone = TimeZone.getTimeZone("America/Chicago");
//		simpleDateFormat.setTimeZone(etTimeZone);
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeZone(etTimeZone);
//		cal.add(Calendar.DATE, -lastDate);
//		return cal.getTime();
//	}
//
//	public static Date getConvertedDate(String dateStr) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SMARTEDIT_DATE);
//		TimeZone etTimeZone = TimeZone.getTimeZone("America/Chicago");
//		simpleDateFormat.setTimeZone(etTimeZone);
//		Date date = new Date();
//		try {
//			date = simpleDateFormat.parse(dateStr);
//		} catch (ParseException e) {
//		}
//		return date;
//	}
}
