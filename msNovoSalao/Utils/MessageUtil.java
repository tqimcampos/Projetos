/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : MessageUtil.java
*Date    : 15/02/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Utils;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class MessageUtil implements Serializable{
  private static final long serialVersionUID = 1L;
  
  private final MessageSource source;
  
  @Autowired
  public MessageUtil( final MessageSource source) {
    this.source = source;
  }
  
  public String get(final String message, final Object ...args) {
    return this.source.getMessage(message, args, Locale.getDefault());
  }
}
