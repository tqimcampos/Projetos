package com.br.msNovoSalao.Repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.msNovoSalao.Domain.Servicos;

public interface TipoServicoRepository  extends JpaRepository<Servicos, Long>, Serializable{
	
	  public Servicos findByIdServico(final Long idServico);
	  public Servicos findByNomeServico( final String nomeServico);
	  
 	    @Query("FROM Servicos s " )
 	 Page<Servicos> search(Pageable pageable);



}
