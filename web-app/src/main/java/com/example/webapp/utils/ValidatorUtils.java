package com.example.webapp.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorUtils {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    public Validator getValidator() {
        return validator;
    }

}
