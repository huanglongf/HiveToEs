package com.gome.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtils {

	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		return yesterday;
	}

	public static List<String> get10days() {
		Calendar cal = Calendar.getInstance();
		List<String> days = new ArrayList<String>();
		for (int i = 10; i > 0; i--) {
			cal.add(Calendar.DATE, -i);
			days.add(new SimpleDateFormat("yyyyMMdd").format(cal.getTime()));
			cal = Calendar.getInstance();
		}
		return days;
	}

	public static void main(String[] args) {
		List<String> get10days = get10days();
		System.out.println(get10days);
	}
}
