/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : SSLUtils.java
*Date    : 04/03/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Utils;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SSLUtils {
  
  private SSLUtils(){}
  
  public static HttpComponentsClientHttpRequestFactory buildHttpRequestFactory() {
    try {
      final TrustStrategy trustStrategy = (final X509Certificate[] chain, final String authType) -> true;
      
      final SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                                                                   .loadTrustMaterial(null, trustStrategy)
                                                                   .build();
      
      final SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
      
      final HttpComponentsClientHttpRequestFactory clientFactory = new HttpComponentsClientHttpRequestFactory();
      clientFactory.setHttpClient( HttpClients.custom()
                                              .setSSLSocketFactory(csf)
                                              .build() );
      
      return clientFactory;
    } catch ( final Exception e) {
      log.error( e.getMessage(), e);
      return null;
    }
  }
}