/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : IQueryPaginationFilter.java
*Date    : 16/02/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Utils.Query;

import java.io.Serializable;

public interface IQueryPaginationFilter extends Serializable{
  public abstract Integer getTamanho();
  public abstract Integer getPagina();
}
