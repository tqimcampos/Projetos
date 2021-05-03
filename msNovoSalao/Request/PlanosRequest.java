package com.br.msNovoSalao.Request;

import java.io.Serializable;
import java.util.Date;

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
public class PlanosRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idPlano;
	private String nomePlano;
	private Float valor;
	private String status;
	private Date dataAlteração;
	
}
