package com.email.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.email.api.utilities.Constants;

public class Test {
//
	private static final String DATE = "yyyy-MM-dd HH:mm:ss";
//	private static final String SMARTEDIT_DATE = "MM/dd/yyyy";`
//
	public static void main(String[] args) throws Exception {
		TimeZone cst =  TimeZone.getTimeZone("CST6CDT");
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		sdf.setTimeZone(cst);
		Date date = new Date(Long.valueOf("1588197632277"));
		System.out.println("Date:------->"+sdf.format(date));
//		long Timestamp =  Instant.now().toEpochMilli();
//		System.out.println("Timestamp:========>"+Timestamp);
	}
}
