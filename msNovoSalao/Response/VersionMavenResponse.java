/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : VersionMavenResponse.java
*Date    : 27/03/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Response;

import java.io.Serializable;
import java.util.Date;

import com.br.msNovoSalao.Utils.ConverterUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public final class VersionMavenResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private String version;
  private String name;

  @JsonFormat(pattern = ConverterUtil.FORMATO_DATA)
  private Date build;
  
  @JsonFormat(pattern = ConverterUtil.FORMATO_DATA)
  private Date now;
}