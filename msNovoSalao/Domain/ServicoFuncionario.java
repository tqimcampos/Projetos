package com.br.msNovoSalao.Domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.ConverterUtil;
import com.br.msNovoSalao.Utils.DateAndTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_servicos_funcionario")
@SequenceGenerator(name = "generator_sq_servicos_funcionario", sequenceName = Constants.SQ_SERVICOS_FUNCIONARIOS, allocationSize = 1)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServicoFuncionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_sq_servicos_funcionario")
	@NotNull
	@Column(name = "id_servicos_funcionario")
	private Long id;

	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "valor")
	private Float valor;
	
	@Column(name = "servicos")
	private String servicos;
	
	@JsonDeserialize(using = DateAndTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dataCadastro;
	
	@JsonDeserialize(using = DateAndTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConverterUtil.FORMATO_DATA)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_alteracao")
	private Date dataAlteracao;
}
