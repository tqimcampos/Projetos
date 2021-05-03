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
public class TipoServicoRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idServico;
	private Long idFuncionario;
	private String nomeServico;
	private Float valor;
	
	//qual o sentido adicionar uma descrição se vc não retorna ela no response?
	private String descricaoServico;
	
	//qual o sentido dessa data alteração?
	private Date dataAlteracao;

}
