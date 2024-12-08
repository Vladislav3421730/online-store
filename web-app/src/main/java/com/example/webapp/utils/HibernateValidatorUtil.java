package com.example.webapp.utils;

import lombok.experimental.UtilityClass;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@UtilityClass
public class HibernateValidatorUtil {

    private final ValidatorFactory factory;

    static {
        factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
    }

    public Validator getValidator(){
        return factory.getValidator();
    }

}
