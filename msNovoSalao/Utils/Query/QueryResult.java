/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : QueryResult.java
*Date    : 12/02/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Utils.Query;

import java.io.Serializable;

import org.springframework.util.MultiValueMap;

import lombok.Getter;

public class QueryResult<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  @Getter
  private final transient T result;
  
  @Getter
  private final transient MultiValueMap<String, String> values;

  public QueryResult(final T result, final MultiValueMap<String, String> values) {
    this.result = result;
    this.values = values;
  }
}
