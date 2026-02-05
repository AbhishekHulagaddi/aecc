package com.tierra.auth.utils;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class IDGeneration {
	
    public static int generateRandomNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
    
    public int GenerateId() {
        int randomNumber = generateRandomNumber();
        return randomNumber;
    }
    
    
    public String generateString() {
    	String generatedString = RandomStringUtils.randomAlphabetic(3);
		return generatedString;
    }

}
