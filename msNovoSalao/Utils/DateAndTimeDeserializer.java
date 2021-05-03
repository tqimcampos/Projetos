/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : DateAndTimeDeserializer.java
*Date    : 27/03/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateAndTimeDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(final JsonParser paramJsonParser, final DeserializationContext paramDeserializationContext)
          throws IOException {
      final String str = paramJsonParser.getText().trim();
      try {
          return new SimpleDateFormat(ConverterUtil.FORMATO_YYY_MM_DD).parse(str);
      } catch (final ParseException e){
        log.error(e.getMessage(),e);
      }
      
      return paramDeserializationContext.parseDate(str);
  }
}
