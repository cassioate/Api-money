package com.tessaro.moneyapi.service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

	TimeUtil(){
		throw new IllegalStateException("Classe utilitária.");
	}

	private static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static LocalDate toLocalDate(String time) {
		if (time != null) {
		return LocalDate.parse(time, formatterDate);
		}
		return null;
	}
	
	public static String formatarData(LocalDate data) {
		if (data != null) {
		return data.format(formatterDate);
		}
		return null;
	}
	
}