package com.br.msNovoSalao.Repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.msNovoSalao.Domain.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>, Serializable{
	
	  public Usuario findById(final Long id);
	  public  Usuario findByEmailAndSenha( final String email, final String senha);
	  public  Usuario findByEmail( final String email);


}
