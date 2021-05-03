package com.br.msNovoSalao.Repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.msNovoSalao.Domain.Planos;

public interface PlanosRepository extends JpaRepository<Planos, Long>, Serializable{

	  public Planos findByIdPlano( final Long idPlano);
	    
	   public Planos findByNomePlano(String nomePlano);
	   
  	    @Query("FROM Planos p WHERE p.status = :status " )
  	 Page<Planos> search(Pageable pageable, @Param("status") String status );
	
}
