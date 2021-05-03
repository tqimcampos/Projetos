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
@Table(name = "tb_funcionario")
@SequenceGenerator(name = "generator_sq_funcionario", sequenceName = Constants.SQ_FUNCIONARIO_SALAO, allocationSize = 1)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_sq_funcionario")
	@NotNull
	@Column(name = "id_funcionario")
	private Long idFuncionario;
	
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(name = "tipo_funcionario")
	private String tipoFuncionario;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "sexo")
	private String sexo;	
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "data_nascimento")
	private String dataNascimento;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "municipio")
	private String municipio;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "rua")
	private String rua;
	
	@Column(name = "numero")
	private String numero;
	
	@Column(name = "complemento")
	private String complemento;
	
	@Column(name = "celular")
	private String celular;
	
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
