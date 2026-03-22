package com.mpodda.thymeleaf_sample.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public final class Generators {
	
	public static String nextString(final int min, final int max) {
		final String latinLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
		final int stringLength = new Random().nextInt(min, max);
		
		return RandomStringUtils.insecure().next(stringLength, latinLetters);
	}
	
	public static Date nextDate() {
		final int thisYear = LocalDate.now().getYear();
		
		final int year = new Random().nextInt((thisYear - 90), (thisYear - 1));
		final int month = new Random().nextInt(1, 12);
		
		int maxDay = 31;
		
		switch (month) {
			case 2:
				if (month % 4 == 0 || month % 1000 == 0) {
					maxDay = 29;
				} else {
					maxDay = 28;
				}
			break;
			case 4: 
			case 6:
			case 9:
			case 11:
				maxDay = 30;
			break;
		}
		
		final int day = new Random().nextInt(1, maxDay);
		
		final LocalDate randomDate = LocalDate.of(year, month, day);
		
		return Date.from(randomDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
