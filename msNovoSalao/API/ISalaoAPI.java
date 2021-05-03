/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : ISalaoAPI.java
*Date    : 07/06/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.API;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.br.msNovoSalao.Response.FuncionarioResponse;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.Swagger2Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;

@Api(tags = Constants.NOVO_SALAO, value = "Salao")
public interface ISalaoAPI {

  @ApiOperation( value = "Cadastra um Cliente", 
                 tags = { Constants.NOVO_SALAO }, 
                 response = FuncionarioResponse.class,
                 httpMethod = Swagger2Constants.POST,
                 produces = MediaType.APPLICATION_JSON_VALUE )
  @ApiResponses(value = {@ApiResponse(code = 200, message = Swagger2Constants.MSG_STATUS_200),
      @ApiResponse(code = 400, message = Swagger2Constants.MSG_STATUS_400),
      @ApiResponse(code = 404, message = Swagger2Constants.MSG_STATUS_404),
      @ApiResponse(code = 401, message = Swagger2Constants.MSG_STATUS_401),
      @ApiResponse(code = 412, message = Swagger2Constants.MSG_STATUS_412),
      @ApiResponse(code = 500, message = Swagger2Constants.MSG_STATUS_500),
      @ApiResponse(code = 503, message = Swagger2Constants.MSG_STATUS_503)}
  )
  public abstract ResponseEntity<FuncionarioResponse> salvar(
                                                          final String clientSecret,
                                                          final String clientId,
                                                          final FuncionarioResponse request);


  @ApiOperation( value = "Consulta um Cliente", 
      tags = {Constants.NOVO_SALAO}, 
      response = FuncionarioResponse.class,
      httpMethod = Swagger2Constants.GET,
      produces = MediaType.APPLICATION_JSON_VALUE )
@ApiResponses(value = {@ApiResponse(code = 200, message = Swagger2Constants.MSG_STATUS_200),
@ApiResponse(code = 400, message = Swagger2Constants.MSG_STATUS_400),
@ApiResponse(code = 404, message = Swagger2Constants.MSG_STATUS_404),
@ApiResponse(code = 401, message = Swagger2Constants.MSG_STATUS_401),
@ApiResponse(code = 412, message = Swagger2Constants.MSG_STATUS_412),
@ApiResponse(code = 500, message = Swagger2Constants.MSG_STATUS_500),
@ApiResponse(code = 503, message = Swagger2Constants.MSG_STATUS_503)}
)
public abstract ResponseEntity<FuncionarioResponse> consultar( final String clientSecret,
                                                         final String clientId,
                                                         final Long codigo)  throws NotFoundException;
  
  @ApiOperation( value = "Altera um Cliente", 
                 tags = {Constants.NOVO_SALAO}, 
                 response = FuncionarioResponse.class,
                 httpMethod = Swagger2Constants.PUT,
                 produces = MediaType.APPLICATION_JSON_VALUE )
  @ApiResponses(value = {@ApiResponse(code = 200, message = Swagger2Constants.MSG_STATUS_200),
      @ApiResponse(code = 400, message = Swagger2Constants.MSG_STATUS_400),
      @ApiResponse(code = 404, message = Swagger2Constants.MSG_STATUS_404),
      @ApiResponse(code = 401, message = Swagger2Constants.MSG_STATUS_401),
      @ApiResponse(code = 412, message = Swagger2Constants.MSG_STATUS_412),
      @ApiResponse(code = 500, message = Swagger2Constants.MSG_STATUS_500),
      @ApiResponse(code = 503, message = Swagger2Constants.MSG_STATUS_503)})
  public abstract ResponseEntity<FuncionarioResponse> alterar(final String clientSecret, final String clientId, final Long codigo, final FuncionarioResponse request) throws NotFoundException;

  @ApiOperation( value = "Exclui um Cliente", 
                 tags = {Constants.NOVO_SALAO}, 
                 response = Void.class,
                 httpMethod = Swagger2Constants.DELETE )
  @ApiResponses(value = {@ApiResponse(code = 200, message = Swagger2Constants.MSG_STATUS_200),
      @ApiResponse(code = 400, message = Swagger2Constants.MSG_STATUS_400),
      @ApiResponse(code = 404, message = Swagger2Constants.MSG_STATUS_404),
      @ApiResponse(code = 401, message = Swagger2Constants.MSG_STATUS_401),
      @ApiResponse(code = 412, message = Swagger2Constants.MSG_STATUS_412),
      @ApiResponse(code = 500, message = Swagger2Constants.MSG_STATUS_500),
      @ApiResponse(code = 503, message = Swagger2Constants.MSG_STATUS_503)})
  public abstract ResponseEntity<Void> excluir( final String clientSecret,
                                                final String clientId,
                                                final Long codigo) throws NotFoundException;
  
  @ApiOperation(value = "Pesquisa de clientes", 
                tags = {Constants.NOVO_SALAO}, 
                response = FuncionarioResponse.class,
                httpMethod = Swagger2Constants.GET)
  @ApiResponses(value = {@ApiResponse(code = 200, message = Swagger2Constants.MSG_STATUS_200),
      @ApiResponse(code = 400, message = Swagger2Constants.MSG_STATUS_400),
      @ApiResponse(code = 404, message = Swagger2Constants.MSG_STATUS_404),
      @ApiResponse(code = 401, message = Swagger2Constants.MSG_STATUS_401),
      @ApiResponse(code = 412, message = Swagger2Constants.MSG_STATUS_412),
      @ApiResponse(code = 500, message = Swagger2Constants.MSG_STATUS_500),
      @ApiResponse(code = 503, message = Swagger2Constants.MSG_STATUS_503)})
  public abstract ResponseEntity<List<FuncionarioResponse>> pesquisar( final String clientSecret,
                                                                   final String clientId,
                                                                   final String nome,
                                                                   final String safra,
                                                                   final String regiao,
                                                                   final String tipoVinho,
                                                                   final String minDataCadastro,
                                                                   final String maxDataCadastro,
                                                                   final Integer tamanho, 
                                                                   final Integer pagina);
}