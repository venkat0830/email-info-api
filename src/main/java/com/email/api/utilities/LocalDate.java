package com.email.api.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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

	public static boolean isMondayToday() {
		Calendar cal = Calendar.getInstance();
		if (Calendar.THURSDAY == cal.get(Calendar.DAY_OF_WEEK)) {
			return true;
		}
		return false;

	}

	public static String getLastUpdatedDate(String frequency, boolean isUnix) {
		TimeZone cstTime = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simple = new SimpleDateFormat();
		if (Boolean.FALSE.equals(isUnix)) {
			cstTime = TimeZone.getTimeZone("CST6CDT");
			cal = Calendar.getInstance(cstTime);
			simple = new SimpleDateFormat(DATE, Locale.US);
			simple.setTimeZone(cstTime);
		}

		if (frequency.equals(Constants.FREQ_DAILY)) {
			cal.add(Calendar.DATE, -1);
			cal.add(Calendar.HOUR_OF_DAY, -(cal.get(Calendar.HOUR_OF_DAY) + 6));
			cal.set(Calendar.MINUTE, -(cal.get(Calendar.MINUTE)));
			cal.set(Calendar.SECOND, -(cal.get(Calendar.SECOND)));

		}
		if (frequency.equals(Constants.FREQ_WEEKLY)) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			cal.add(Calendar.DATE, -7);
			cal.add(Calendar.HOUR_OF_DAY, -(cal.get(Calendar.HOUR_OF_DAY) + 6));
			cal.set(Calendar.MINUTE, -(cal.get(Calendar.MINUTE)));
			cal.set(Calendar.SECOND, -(cal.get(Calendar.SECOND)));
		}
		if (Boolean.TRUE.equals(isUnix)) {
			long time = cal.getTimeInMillis();
			return Long.toString(time);
		}

		Date dailyDateTime = cal.getTime();
		return simple.format(dailyDateTime);
	}

	public static String getCurrentDay(String frequency, boolean isUnix) {
		TimeZone cstTime = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simple = new SimpleDateFormat();
		if (Boolean.FALSE.equals(isUnix)) {
			cstTime = TimeZone.getTimeZone("CST6CDT");
			cal = Calendar.getInstance(cstTime);
			simple = new SimpleDateFormat(DATE, Locale.US);
			simple.setTimeZone(cstTime);
		}

		if (frequency.equals(Constants.FREQ_DAILY)) {
			cal.add(Calendar.HOUR_OF_DAY, 5);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);

		}
		if (frequency.equals(Constants.FREQ_WEEKLY)) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			cal.add(Calendar.HOUR_OF_DAY, 5);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		}
		if (Boolean.TRUE.equals(isUnix)) {
			long time = cal.getTimeInMillis();
			return Long.toString(time);
		}

		Date dailyDateTime = cal.getTime();
		return simple.format(dailyDateTime);
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
}
