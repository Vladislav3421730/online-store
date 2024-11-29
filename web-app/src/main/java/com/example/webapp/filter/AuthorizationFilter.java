package com.example.webapp.filter;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;

@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {
}
