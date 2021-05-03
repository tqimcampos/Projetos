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
@Table(name = "tb_servicos")
@SequenceGenerator(name = "generator_sq_servicos", sequenceName = Constants.SQ_SERVICOS, allocationSize = 1)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Servicos implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_sq_servicos")
	@NotNull
	@Column(name = "id_servico")
	private Long idServico;
	
	@Column(name = "id_funcionario")
	private Long idFuncionario;
	
	@Column(name = "nome_servico")
	private String nomeServico;
	
	@Column(name = "valor")
	private Float valor;
	
	@Column(name = "descricao_servico")
	private String descricaoServico;
	
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
