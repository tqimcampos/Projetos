/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : PageUtil.java
*Date    : 27/03/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Utils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.br.msNovoSalao.Utils.Query.IQueryPaginationFilter;

public class PageUtil {

  public static final int PAGINACAO_PADRAO = 50;

  private PageUtil() {}

  public static Pageable getPageableWithDefaultLimit10( final Optional<Integer> page, final Optional<Integer> size, final Optional<String> sort ) {

    Integer numPage = 0;
    Integer numSize = 10;
    Pageable pageRequest;

    if (page.isPresent())
      numPage = page.get();

    if (size.isPresent())
      numSize = size.get();

    if (sort.isPresent()) {

      final String[] sorts = sort.get().split( "," );

      if (sorts[1].equalsIgnoreCase( "DESC" )) {
        pageRequest = new PageRequest( numPage, numSize, new Sort( Sort.Direction.DESC, sorts[0] ) );
      } else {
        pageRequest = new PageRequest( numPage, numSize, new Sort( Sort.Direction.ASC, sorts[0] ) );
      }

    } else {
      pageRequest = new PageRequest( numPage, numSize );
    }

    return pageRequest;
  }

  public static Pageable getPageableWithDefaultLimit20( final Optional<Integer> page, final Optional<Integer> size, final Optional<String> sort ) {

    Integer numPage = 0;
    Integer numSize = 20;
    Pageable pageRequest;

    if (page.isPresent())
      numPage = page.get();

    if (size.isPresent())
      numSize = size.get();

    if (sort.isPresent()) {

      final String[] sorts = sort.get().split( "," );

      if (sorts[1].equalsIgnoreCase( "DESC" )) {
        pageRequest = new PageRequest( numPage, numSize, new Sort( Sort.Direction.DESC, sorts[0] ) );
      } else {
        pageRequest = new PageRequest( numPage, numSize, new Sort( Sort.Direction.ASC, sorts[0] ) );
      }
    } else {
      pageRequest = new PageRequest( numPage, numSize );
    }

    return pageRequest;
  }

  public static Integer getNumeroUltimoElemento( final Page<?> page ) {
    return getNumeroPrimeiroElemento( page ) + (page.getNumberOfElements() - 1);
  }

  public static Integer getNumeroPrimeiroElemento( final Page<?> page ) {
    return page.isFirst() ? 1 : ((((page.getNumber() + 1) * page.getSize()) + 1) - page.getSize());
  }

  public static void paginacao( final Criteria criteria, final Integer pagina, final Integer tamanho, final int defaultPagination ) {
    // Paginação
    if (!Objects.isNull( tamanho )) {
      criteria.setMaxResults( tamanho );
    } else {
      criteria.setMaxResults( defaultPagination );
    }

    if (!Objects.isNull( pagina ) && pagina.intValue() > BigDecimal.ZERO.intValue() ) {
      final int controlePagina = pagina - 1;
      
      if (!Objects.isNull( tamanho )) {
        criteria.setFirstResult( controlePagina * tamanho );
      } else {
        criteria.setFirstResult( controlePagina * defaultPagination );
      }
    }
  }

  public static void paginacao( final Criteria criteria, final Integer pagina, final Integer tamanho ) {
    paginacao(criteria, pagina, tamanho, PageUtil.PAGINACAO_PADRAO);
  }
  
  
  public static MultiValueMap<String, String> buildPaginacao( final Criteria criteria, final int totalRegistros, final IQueryPaginationFilter filtro ) {
    final Integer tamanho = Objects.isNull( filtro.getTamanho() ) ? PageUtil.PAGINACAO_PADRAO : filtro.getTamanho();

    // Seta paginação do criteria
    final Integer pagina = filtro.getPagina();

    PageUtil.paginacao( criteria, pagina, tamanho, PageUtil.PAGINACAO_PADRAO );
    final int totalPaginas = calculatorTotalPages( totalRegistros, tamanho );

    final MultiValueMap<String, String> values = new LinkedMultiValueMap<>();

    values.add( Constants.PAGINA, Objects.toString( pagina, BigDecimal.ONE.toString() ) );
    values.add( Constants.TAMANHO, Objects.toString( tamanho, Constants.EMPTY ) );
    values.add( Constants.TOTAL_PAGINAS, String.valueOf( totalPaginas ) );
    values.add( Constants.TOTAL_REGISTROS, String.valueOf( totalRegistros ) );

    return values;
  }

  public static int calculatorTotalPages( final int quantidadeRegistros, final int totalPorPaginas ) {
    final BigDecimal qtdeRegistros = new BigDecimal( quantidadeRegistros );

    final BigDecimal qtdePorPagina = new BigDecimal( totalPorPaginas );

    final BigDecimal total = qtdeRegistros.divide( qtdePorPagina, BigDecimal.ROUND_UP );

    return total.intValue();
  }
}
