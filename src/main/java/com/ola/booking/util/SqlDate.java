package com.ola.booking.util;

import java.sql.Date;
import java.sql.Time;

public class SqlDate {
	public static Date currentSqlDate() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		return date;
	}
	
	public static Time currentSqlTime() {
		long time = System.currentTimeMillis();
		Time sqlTime = new Time(time);
		return sqlTime;
	}
}
