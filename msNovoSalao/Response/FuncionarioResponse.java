package com.br.msNovoSalao.Response;

import java.io.Serializable;
import java.util.Date;

import com.br.msNovoSalao.Utils.ConverterUtil;
import com.br.msNovoSalao.Utils.DateAndTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FuncionarioResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idFuncionario;
	private Long idUsuario;
	private String tipoFuncionario;
	private String nome;
	private String email;
	private String sexo;
	private String senha;
	private String status;
	private String cpf;
	private String rg;
	private String dataNascimento;
	private String celular;
	private String estado;
	private String municipio;
	private String cep;
	private String bairro;
	private String rua;
	private String numero;
	private String complemento;

	@ApiModelProperty(value = "Data de Cadastro do Registro")
	@JsonDeserialize(using = DateAndTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
	private Date dataCadastro;

	@ApiModelProperty(value = "Data de alteracao do Registro")
	@JsonDeserialize(using = DateAndTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
	private Date dataAlteracao;

}
