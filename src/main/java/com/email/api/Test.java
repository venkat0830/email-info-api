//package com.email.api;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//public class Test {
//
//
//	private static final String DATE = "yyyy-MM-dd HH:mm:ss";
//
//	public static Date getLastUpdatedDate(int lastDate) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE);
//		Calendar cal = Calendar.getInstance();
////		cal.add(Calendar.DATE, -7);
////		cal.add(Calendar.HOUR, -2);
//		cal.add(Calendar.HOUR_OF_DAY, -170);
//		Date dailyDateTime = cal.getTime();
//		Date formatDate = new Date();
//		try {
//			formatDate = simpleDateFormat.parse(simpleDateFormat.format(dailyDateTime));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return formatDate;
//	}
//
//	public static Date fromateDate(String lastDate) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE);
//		Date formatDate = new Date();
//		try {
//			formatDate = simpleDateFormat.parse(simpleDateFormat.format(lastDate));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return formatDate;
//	}
//
//	public static List<String> getDateList() {
//		List<String> dateList = new ArrayList<>();
//		dateList.add("2019-11-12 19:56:33");
//		dateList.add("2019-11-18 9:03:33");
//		dateList.add("2019-11-10 14:33:33");
//		return dateList;
//	}
//
//	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat objSDF = new SimpleDateFormat(DATE);
//		Date yesterdayDate = getLastUpdatedDate(-1);
//		List<String> dateList = getDateList();
//		for (String string : dateList) {
//			Date dbDate = objSDF.parse(string);
//			if (yesterdayDate.before(dbDate)) {
//				System.out.println("I>>>>" + string);
//			}
//		}
//	}
//
//
//}
