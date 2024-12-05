package com.example.webapp.validator;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Validator {

    public long validateLong(String isParam){
        try {
            return Long.parseLong(isParam);
        } catch (NumberFormatException e) {
            log.error("Failed to parse in long {}",isParam);
            throw new RuntimeException("Invalid param " + isParam, e);
        }

    }

    public double validateDouble(String isParam){
        try {
            return Double.parseDouble(isParam);
        } catch (NumberFormatException e) {
            log.error("Failed to parse in double {}",isParam);
            throw new RuntimeException("Invalid param " + isParam, e);
        }

    }

    public int validateInt(String isParam){
        try {
            return Integer.parseInt(isParam);
        } catch (NumberFormatException e) {
            log.error("Failed to parse in int {}",isParam);
            throw new RuntimeException("Invalid param" + isParam, e);
        }

    }
}
