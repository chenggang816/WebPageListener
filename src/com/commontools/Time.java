package com.commontools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	public static String getNowTimeString() {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String nowString = df.format(now);
		return nowString;
	}
}
