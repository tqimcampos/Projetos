package com.br.msNovoSalao.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.msNovoSalao.Domain.ServicoFuncionario;

public interface ServicoFuncionarioRepository extends JpaRepository<ServicoFuncionario, Long>, Serializable {

	@Query("FROM ServicoFuncionario f ")
	Page<ServicoFuncionario> search(Pageable pageable);

	public ServicoFuncionario findById(final Long id);

	@Query("FROM ServicoFuncionario servFunc WHERE servFunc.cpf = :cpf AND servFunc.dataCadastro BETWEEN :dataInicio AND :dataFim")
	 List<ServicoFuncionario> buscarServicoPorCpfAndData(final String cpf,final Date dataInicio, final Date dataFim);

}
