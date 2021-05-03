/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : ReflectionUtils.java
*Date    : 27/03/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Utils.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionUtils {

  private ReflectionUtils() {}
  
  public static <R, E> void populateFields(final R request, final E entity) {
    if (Objects.isNull(request) || Objects.isNull(entity)) {
        return;
    }

    try {
        final Class<?> classeRequest = request.getClass();
        final Class<?> classeEntity = entity.getClass();
        for (final Field campo : classeRequest.getDeclaredFields()) {
            campo.setAccessible(true);
            populate(classeEntity, campo, request, entity);
        }
    } catch (final Exception e) {
        log.error(e.getMessage(), e);
    }
 }

  private static<R, E> void populate( final Class<?> classeEntity, final Field campo, final R request, final E entity ) {
    try {
        final Field campoEntity = classeEntity.getDeclaredField(campo.getName());
        if (!Objects.isNull(campoEntity)) {
            campoEntity.setAccessible(true);
            if( campoEntity.getName().equals("serialVersionUID")) {
                return;
            }
            
            final Object value = campo.get(request);
            if ( !Objects.isNull(value) && !StringUtils.isEmpty( value.toString()) ) {
                setValue(campoEntity, entity, value);
            }
        }
    } catch (final Exception e) {
        log.error(e.getMessage(), e);
    }   
  }
  
  /**
   * Método responśavel por retornar todos os campos da classe de acordo com a lista de anotações recebidas.
   * 
   * @param clazz Classe de varredura das anotações
   * @param anotacoes Anotação que será pesquisa na classe
   * @return conjunto de atributos que contém a anotação recebida por parâmetro.
   */
  @SafeVarargs
  public static Set<Field> fields( final Class<?> clazz, final Class<? extends Annotation>... anotacoes ) {
    try {
      Class<?> classe = clazz;
      if ( Objects.isNull( classe )) {
        return Collections.emptySet();
      }

      final Set<Field> campos = Collections.synchronizedSet( new HashSet<>() );
      while (!Objects.isNull( classe )) {
        
        for (final Field campo : classe.getDeclaredFields()) {
          campo.setAccessible( true );
          for (final Class<? extends Annotation> anotacao : anotacoes) {
            if (campo.isAnnotationPresent( anotacao )) {
              campos.add( campo );
            }
          }
        }
        classe = classe.getSuperclass();
      }
      return campos;
    } catch (final Exception e) {
      log.error( e.getMessage(), e );
      throw new IllegalArgumentException( e.getMessage() );
    }
  }

  /**
   * Método responsável por setar o valor do atributo no objeto
   * 
   * @param campo Campo do Objeto
   * @param entidade Objeto origem para Set
   * @param value valor que será setado no objeto origem
   */
  public static <T> void setValue(final Field campo, final T entidade, final Object value) {
    if (Objects.isNull( campo ) || Objects.isNull( value )) {
        return;
    }
    try {
        campo.setAccessible(true);
        campo.set(entidade, getValueByType(campo.getType(), value));
    } catch (final Exception e) {
        log.error( e.getMessage(), e );
    }
}
  

  /**
   * Cria uma instância vazia
   *
   * @param entidade
   * @param classe
   */
  public static <T> T newInstance( final Class<T> classe ) {
    if (Objects.isNull( classe )) {
      throw new IllegalArgumentException();
    }
    try {
      return classe.newInstance();
    } catch (final Exception e) {
      log.error( e.getMessage(), e );
      throw new IllegalArgumentException( e );
    }
  }

  public static Object getValueByType( final Class<?> tipoAtributo, final Field field, final Object value ){
    try {
      final Object valor = field.get( value );
      return getValueByType( tipoAtributo, valor );
    } catch (final Exception e) {
      log.error( e.getMessage(), e );
      return null;
    }
  }
  
  /**
   * Método responsável por retornar o valor do atributo de acordo com o tipo.
   * 
   * @param tipoAtributo Tipo do atributo do campo
   * @param valor Valor do atributo
   * @return o valor de convertido pra o tipo correspondente
   * @throws ParseException Caso ocorra algum erro de parseDate
   */
  public static Object getValueByType( final Class<?> tipoAtributo, final Object valor ){
    if (Objects.isNull( valor )) {
      return null;
    } else if (tipoAtributo.isAssignableFrom( String.class )) {
      return valor.toString().trim();
    } else if (tipoAtributo.isAssignableFrom( Long.class )) {
      return Long.parseLong( valor.toString() );
    } else if (tipoAtributo.isAssignableFrom( Integer.class )) {
      return Integer.parseInt( valor.toString() );
    } else if (tipoAtributo.isAssignableFrom( BigDecimal.class )) {
      return new BigDecimal( valor.toString() );
    } else if (tipoAtributo.isAssignableFrom( Date.class )) {
      return valor;
    }
    return valor;
  }
}