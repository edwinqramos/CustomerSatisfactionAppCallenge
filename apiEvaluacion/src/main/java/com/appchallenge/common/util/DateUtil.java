package com.appchallenge.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static final String FORMATO_DIA_DDMMYYYY = "dd-MM-yyyy";

	/**
	 * Convertir String a Date con formato parametrizado
	 * 
	 * @param date
	 * @param format
	 * @return date
	 */
	public static Date stringToDate(String date, String format) {
		if (date == null) {
			return null;
		}

		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Converitr Date a String con formato parametrizado 
	 * @param date
	 * @param format
	 * @return String
	 * @throws Exception
	 */
	public static String dateToString(Date date,String format) throws Exception {
		if(date == null) {
			return null;
		}
		return new SimpleDateFormat(format, new Locale("es", "ES")).format(date);
	}

}
