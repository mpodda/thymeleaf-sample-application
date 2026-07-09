package com.mpodda.thymeleaf_sample.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mpodda.thymeleaf_sample.domain.dto.PersonDto;

public final class RandomDateGenerator {
	public static List<PersonDto> generateRandomPersons(final int numberOfRecords) {
		List<PersonDto> personDtoList = new ArrayList<PersonDto>(numberOfRecords);
		
		for (int recordNumber = 0; recordNumber < numberOfRecords; recordNumber++) {
			/*
			personDtoList.add(new PersonDto (
				Math.abs(new Random().nextLong()),
				new StringBuilder().append(Generators.nextString(5, 12)).append(" ").append(Generators.nextString(5, 12)).toString(),
				new Random().nextInt(1, 90),
				Generators.nextString(5, 15),
				Generators.nextDate()
			));
			*/
		}
		
		return personDtoList;
	}
}
