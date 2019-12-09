package com.email.api.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class LocalDate {

	private static final String DATE = "yyyy-MM-dd HH:mm:ss";

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

	public static Date getLastUpdatedDate(String frequency) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE);
		Calendar cal = Calendar.getInstance();
		if (frequency.equals(Constants.FREQ_DAILY)) {
		cal.add(Calendar.DATE, -1);
		cal.add(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		}
		if (frequency.equals(Constants.FREQ_WEEKLY)) {
		cal.add(Calendar.DATE, -7);
		cal.add(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
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
	
	public static Date getCurrentDate() {
		Calendar cal =  Calendar.getInstance();
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date date =  cal.getTime();
		return date;
		
	}

}
