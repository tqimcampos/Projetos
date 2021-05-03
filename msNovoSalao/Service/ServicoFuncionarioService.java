package com.br.msNovoSalao.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.br.msNovoSalao.Domain.ServicoFuncionario;
import com.br.msNovoSalao.Exceptions.PreconditionFailedException;
import com.br.msNovoSalao.Repository.ServicoFuncionarioRepository;
import com.br.msNovoSalao.Request.ServicoFuncionarioRequest;
import com.br.msNovoSalao.Response.ServicoFuncionarioResponse;
import com.br.msNovoSalao.Response.SubTotalResponse;
import com.br.msNovoSalao.Utils.ConverterUtil;

@Lazy
@Service
public class ServicoFuncionarioService {
	
	

private final ServicoFuncionarioRepository repository;
	
	@Autowired
	public ServicoFuncionarioService(ServicoFuncionarioRepository repository) {
		this.repository = repository;
	}
	
	public ServicoFuncionarioResponse cadastrar(final ServicoFuncionarioRequest request) {

		ServicoFuncionario domain = new ServicoFuncionario();
				
		convertRequestServicoToDomain(request, domain);
		
		ServicoFuncionario domainSalvo = this.repository.save(domain);
				
		ServicoFuncionarioResponse response = converterDomainToResponseServicos(domainSalvo);

		return response;

	}
	
    public Page<ServicoFuncionario> getAll(Pageable pageable) {
        
        return repository.search(
       		pageable);
    }
    
	public ServicoFuncionarioResponse findById(final Long id) {

		final ServicoFuncionario servico = this.repository.findById(id);
		if (Objects.isNull(servico)) {
		       throw new PreconditionFailedException( "Id servico não existe");

		}
		
		ServicoFuncionarioResponse response = converterDomainToResponseServicos(servico);

		return response;
	}
	
	public ServicoFuncionarioResponse updateServico(final Long id, final ServicoFuncionarioRequest request){

		final ServicoFuncionario domain = this.repository.findById(id);
		
		if (Objects.isNull(domain)) {
		       throw new PreconditionFailedException( "Id servico não existe");
		}
		
		validateRquestUpdate(request, domain);
		
		domain.setDataAlteracao(ConverterUtil.nowTime());
		this.repository.save(domain);

		ServicoFuncionarioResponse response = converterDomainToResponseServicos(domain);

		return response;
	}
	
	public SubTotalResponse servicosFuncionario(final String cpf,final Date dataInicio,final Date dataFim){
		
		List<ServicoFuncionario> listaServico = this.repository.buscarServicoPorCpfAndData(cpf,dataInicio,dataFim);
		
		for (ServicoFuncionario servicoFuncionario : listaServico){
			servicoFuncionario.setDataCadastro(servicoFuncionario.getDataCadastro());
			servicoFuncionario.setDataAlteracao(servicoFuncionario.getDataAlteracao());
		}
		
		float valorTotal = 0;
		
	    double percentual = 40.0 / 100.0; 

		for (ServicoFuncionario servico : listaServico) {
			valorTotal = valorTotal + servico.getValor();		
		}
	    double valorFinal = valorTotal - (percentual * valorTotal);
	    	    
	    BigDecimal valorBigDecimal = new BigDecimal(valorFinal);

	    valorBigDecimal = valorBigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		
		SubTotalResponse subTotal = new SubTotalResponse();
		subTotal.setListaServico(listaServico);
		subTotal.setTotalServicos(listaServico.size());
		subTotal.setValorTotal(valorBigDecimal);
		
		return subTotal;
			
	}
	
private void convertRequestServicoToDomain(final ServicoFuncionarioRequest request, ServicoFuncionario servico) {

		servico.setCpf(request.getCpf());
		servico.setValor(request.getValor());
		servico.setServicos(request.getServicos());
		servico.setDataCadastro(ConverterUtil.nowTime());
		servico.setDataAlteracao(ConverterUtil.nowTime());
	}

private ServicoFuncionarioResponse converterDomainToResponseServicos(ServicoFuncionario domain) {

	ServicoFuncionarioResponse response = new ServicoFuncionarioResponse();
	response.setCpf(domain.getCpf());
	response.setValor(domain.getValor());
	response.setServicos(domain.getServicos());
	response.setDataCadastro(domain.getDataCadastro());
	response.setDataAlteracao(domain.getDataAlteracao());

	return response;
}
private void validateRquestUpdate(final ServicoFuncionarioRequest request, final ServicoFuncionario domain) {
	
	if(!StringUtils.isEmpty(request.getServicos())) {
		domain.setServicos(request.getServicos());
	}
	if(!StringUtils.isEmpty(request.getValor())) {
		domain.setValor(request.getValor());
	}
	if(!StringUtils.isEmpty(request.getCpf())) {
		domain.setCpf(request.getCpf());
	}
	
  }
}
