package com.example.webapp.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {

    private final String JSP_FORMAT="/WEB-INF/views/%s.jsp";

    public String getPath(String jsp){
       return JSP_FORMAT.formatted(jsp);
    }

}
