package com.br.msNovoSalao.Repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.msNovoSalao.Domain.Funcionario;



public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, Serializable{
	
	  public abstract Funcionario findByIdFuncionario( final Long idFuncionario);
	  	    
	   public Funcionario findByCpf(String cpf);

	   
			   	    @Query("FROM Funcionario f WHERE f.status = :status " )
			   	 Page<Funcionario> search(Pageable pageable, @Param("status") String status );


}
