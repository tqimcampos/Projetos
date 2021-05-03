package com.br.msNovoSalao.Request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class FuncionarioRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idFuncionario;
	private Long idUsuario;
	private String tipoFuncionario;
	private String nome;
	private String email;
	private String sexo;
	private String senha;
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
}
