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
public class PlanosResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idPlano;
	private String nomePlano;
	private Float valor;
	private String status;
	
	@ApiModelProperty(value = "Data de Cadastro do Registro")
	@JsonDeserialize(using = DateAndTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
	private Date dataCadastro;

	@ApiModelProperty(value = "Data de alteracao do Registro")
	@JsonDeserialize(using = DateAndTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
	private Date dataAlteracao;
}
