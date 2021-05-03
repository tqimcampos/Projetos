/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : SimpleCORSFilter.java
*Date    : 29/03/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SimpleCORSFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(final HttpServletRequest hsr, final HttpServletResponse hsr1, final FilterChain fc)
      throws ServletException, IOException {
    String origin = hsr.getHeader("origin");
    origin = (origin == null || origin.equals("")) ? "null" : origin;
    hsr1.addHeader("Access-Control-Allow-Origin", "*");
    hsr1.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, DELETE, PATCH, HEAD, OPTIONS");
    hsr1.addHeader("Access-Control-Allow-Credentials", "true");
    hsr1.addHeader("Access-Control-Allow-Headers", "Authorization, origin, client_id, client_secret, Client-Id, access_token, content-type, accept, apikey, x-requested-with");

    fc.doFilter(hsr, hsr1);
  }
}
