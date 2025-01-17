package com.example.webapp.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.ResourceBundle;

@UtilityClass
public class ResourceBundleUtils {

    private final String BUNDLE_NAME = "messages";

    public ResourceBundle get(HttpServletRequest req) {
        Locale locale = (Locale) req.getSession().getAttribute("locale");
        if (locale == null) {
            locale = Locale.forLanguageTag("ru");
        }
        return ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }
}
