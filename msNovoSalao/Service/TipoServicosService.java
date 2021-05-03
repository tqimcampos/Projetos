package com.br.msNovoSalao.Service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.br.msNovoSalao.Domain.Servicos;
import com.br.msNovoSalao.Exceptions.ErrorCodes;
import com.br.msNovoSalao.Exceptions.PreconditionFailedException;
import com.br.msNovoSalao.Repository.TipoServicoRepository;
import com.br.msNovoSalao.Request.TipoServicoRequest;
import com.br.msNovoSalao.Request.UpdateServicoRequest;
import com.br.msNovoSalao.Response.TipoServicoResponse;
import com.br.msNovoSalao.Utils.ConverterUtil;

@Lazy
@Service
public class TipoServicosService {

	private final TipoServicoRepository repository;
	
	@Autowired
	public TipoServicosService(TipoServicoRepository repository) {
		this.repository = repository;
	}
	
	public TipoServicoResponse cadastrarservico(final TipoServicoRequest request) {

		this.validarServico(request);

		Servicos serv = this.repository.findByNomeServico(request.getNomeServico());
		if (!StringUtils.isEmpty(serv)) {
		       throw new PreconditionFailedException( "Já existe um servico cadastrado com este nome!");
		}
		
		Servicos servicoDomain = new Servicos();
				
		convertRequestServicoToDomain(request, servicoDomain);
		
		Servicos domain = this.repository.save(servicoDomain);
		domain.setIdFuncionario(request.getIdFuncionario());
		this.repository.save(domain);
				
		TipoServicoResponse response = converterDomainToResponseServicos(domain);

		return response;

	}
	
	public TipoServicoResponse findById(final Long idServico) {

		final Servicos service = this.repository.findByIdServico(idServico);
		if (Objects.isNull(service)) {
		       throw new PreconditionFailedException( "Id serviço não existente");
		}
		
		TipoServicoResponse response = converterDomainToResponseServicos(service);

		return response;
	}
	
	public TipoServicoResponse updateServico(final Long idServico, final UpdateServicoRequest request){

		final Servicos domain = this.repository.findByIdServico(idServico);
		
		if (Objects.isNull(domain)) {
		       throw new PreconditionFailedException( "Id servico não existente");
		}
		
		validateRquestUpdate(request, domain);
		
		domain.setDataAlteracao(ConverterUtil.nowTime());
		
		this.repository.save(domain);

		TipoServicoResponse response = converterDomainToResponseServicos(domain);

		return response;
	}
	
	public void deletar(final Long idServico){
		
		final Servicos servicoDomain = this.repository.findByIdServico(idServico);
		if (Objects.isNull(servicoDomain)) {
		       throw new PreconditionFailedException( "Id servico não existente");
		}		
	    this.repository.delete(servicoDomain);
	}
	
    public Page<Servicos> search(Pageable pageable) {
        
        return repository.search(
       		pageable);
    }
		
	private TipoServicoResponse converterDomainToResponseServicos(Servicos domain) {

		TipoServicoResponse response = new TipoServicoResponse();
		response.setIdServico(domain.getIdServico());
		response.setNomeServico(domain.getNomeServico());
		response.setValor(domain.getValor());
		response.setIdFuncionario(domain.getIdFuncionario());
		response.setDataCadastro(domain.getDataCadastro());
		response.setDataAlteracao(domain.getDataAlteracao());

		return response;
	}
	
	private void convertRequestServicoToDomain(final TipoServicoRequest request, Servicos servico) {
		
		// qual o sentido desse idServiço no request?
		servico.setIdServico(request.getIdServico());
		
		
		servico.setNomeServico(request.getNomeServico());
		servico.setValor(request.getValor());
		servico.setIdFuncionario(request.getIdFuncionario());
		servico.setDescricaoServico(request.getDescricaoServico());
		servico.setDataCadastro(ConverterUtil.nowTime());
	}
	
	private void validarServico(final TipoServicoRequest request) {
		
		if (StringUtils.isEmpty(request.getIdFuncionario())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getNomeServico())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getValor())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getDescricaoServico())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
	
	}
	
	private void validateRquestUpdate(final UpdateServicoRequest request, final Servicos domain) {
		if(!StringUtils.isEmpty(request.getNomeServico())) {
			domain.setNomeServico(request.getNomeServico());
		}
		
		if(!StringUtils.isEmpty(request.getIdFuncionario())) {
			domain.setIdFuncionario(request.getIdFuncionario());
		}
		
		if(!StringUtils.isEmpty(request.getDescricaoServico())) {
			domain.setDescricaoServico(request.getDescricaoServico());
		}
		
		if(!StringUtils.isEmpty(request.getValor())) {
			domain.setValor(request.getValor());
		}
		
	}	
	
}
