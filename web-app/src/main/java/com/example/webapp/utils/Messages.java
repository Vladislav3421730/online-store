package com.example.webapp.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

    public final String ERROR_INVALID_INPUT = "login.error.invalid_input";
    public final String ERROR_INVALID_CREDENTIALS = "login.error.invalid_credentials";
    public final String ERROR_USER_BANNED = "login.error.banned";
    public final String INVALID_FIELDS = "registration.error.invalid_fields";
    public final String EMAIL_ALREADY_IN_USE = "registration.error.email_exists";
    public final String PHONE_ALREADY_IN_USE = "registration.error.phone_exists";
    public final String ERROR_PASSWORD_MISS = "registration.error.password_mismatch";
    public final String ERROR_MESSAGE = "product.error.invalid_id";
    public final String SUCCESS_MESSAGE = "order.success";
    public final String FAILED_PAYMENT_MESSAGE =  "order.payment_failed";
    public final String ERROR_MESSAGE_INCREMENT = "cart.error.quantity_exceeded";

}
