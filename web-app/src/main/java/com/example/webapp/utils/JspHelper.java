package com.example.webapp.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {

    private final static String JSP_FORMAT="/WEB-INF/views/";

    public String getPath(String jsp){
       return JSP_FORMAT+jsp+".jsp";
    }

}
