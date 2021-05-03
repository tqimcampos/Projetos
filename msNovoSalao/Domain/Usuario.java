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
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_usuario")
@SequenceGenerator(name = "generator_sq_usuario", sequenceName = Constants.SQ_USUARIO_SALAO, allocationSize = 1)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_sq_usuario")
	@NotNull
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "tipo_usuario")
	private String tipoUsuario;
	
	@Column(name = "senha")
	private String senha;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_alteracao")
	private Date dataAlteracao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ultimo_login")
	private Date ultimoLoguin;

}
