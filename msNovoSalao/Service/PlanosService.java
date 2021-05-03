package com.br.msNovoSalao.Service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.br.msNovoSalao.Domain.Planos;
import com.br.msNovoSalao.Exceptions.ErrorCodes;
import com.br.msNovoSalao.Exceptions.PreconditionFailedException;
import com.br.msNovoSalao.Repository.PlanosRepository;
import com.br.msNovoSalao.Request.PlanosRequest;
import com.br.msNovoSalao.Request.UpdatePlanoRequest;
import com.br.msNovoSalao.Response.PlanosResponse;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.ConverterUtil;

@Lazy
@Service
public class PlanosService {
	
	private final PlanosRepository repository;
	
	@Autowired
	public PlanosService(PlanosRepository repository){
		this.repository = repository;
	}
	
	public PlanosResponse cadastrarPlano(final PlanosRequest request) {
		
		this.validarRequestPLanos(request);
				
		Planos plans = this.repository.findByNomePlano(request.getNomePlano());
		if (!StringUtils.isEmpty(plans)) {
		       throw new PreconditionFailedException( "Já existe um plano cadastrado, com esse nome!");
		}
		
		Planos planos = new Planos();
		
		convertRequestPlanosToDomain(request, planos);
		
		Planos domain = this.repository.save(planos);

		PlanosResponse response = converterDomainToResponsePlanos(domain);

		return response;
	}
	
	public PlanosResponse findById(final Long idPlano) {

		final Planos plans = this.repository.findByIdPlano(idPlano);
		if (Objects.isNull(plans)) {
		       throw new PreconditionFailedException( "Id plano não existente");

		}
		
		PlanosResponse response = converterDomainToResponsePlanos(plans);

		return response;
	}
	
	public PlanosResponse updatePlano(final Long idPlano, final UpdatePlanoRequest request){

		final Planos domain = this.repository.findByIdPlano(idPlano);
		
		if (Objects.isNull(domain)) {
		       throw new PreconditionFailedException( "Id plano não existente");
		}
		
		validateRquestUpdate(request, domain);
		
		domain.setDataAlteracao(ConverterUtil.nowTime());
		this.repository.save(domain);

		PlanosResponse response = converterDomainToResponsePlanos(domain);

		return response;
	}
	
	public void inativar(final Long idPlano){
		
		final Planos planosDomain = this.repository.findByIdPlano(idPlano);
		
		if (Objects.isNull(planosDomain)) {
		       throw new PreconditionFailedException( "Id Plano não existe");
		}
		planosDomain.setStatus(Constants.INATIVO);
		planosDomain.setDataAlteracao(ConverterUtil.nowTime());
		
	    this.repository.save(planosDomain);
	}
	
    public Page<Planos> search(Pageable pageable, String status) {
        
        return repository.search(
       		pageable, status);
    }
    
	public PlanosResponse ativarPlano(final Long idPlano){
		
		final Planos planosdomain = this.repository.findByIdPlano(idPlano);
		
		if (Objects.isNull(planosdomain)) {
		       throw new PreconditionFailedException( "Id não existente");
		}
		
		planosdomain.setStatus(Constants.ATIVO);
		planosdomain.setDataAlteracao(ConverterUtil.nowTime());
		this.repository.save(planosdomain);

		PlanosResponse response = converterDomainToResponsePlanos(planosdomain);

		return response;
	}
	
	private void convertRequestPlanosToDomain(final PlanosRequest request, Planos plano) {
		plano.setNomePlano(request.getNomePlano());
		plano.setValor(request.getValor());
		plano.setStatus(Constants.ATIVO);
		plano.setDataCadastro(ConverterUtil.nowTime());
	}	
	
	private PlanosResponse converterDomainToResponsePlanos(Planos domain) {

		PlanosResponse response = new PlanosResponse();
		response.setIdPlano(domain.getIdPlano());
		response.setNomePlano(domain.getNomePlano());
		response.setStatus(domain.getStatus());
		response.setValor(domain.getValor());
		response.setDataCadastro(domain.getDataCadastro());
		response.setDataAlteracao(domain.getDataAlteracao());
		return response;
	}
	
	private void validateRquestUpdate(final UpdatePlanoRequest request, final Planos domain) {
		
		if(!StringUtils.isEmpty(request.getNomePlano())) {
			domain.setNomePlano(request.getNomePlano());
		}
		if(!StringUtils.isEmpty(request.getStatus())) {
			domain.setStatus(request.getStatus());
		}
		if(!StringUtils.isEmpty(request.getValor())) {
			domain.setValor(request.getValor());
		}
		
	}	
	
	private void validarRequestPLanos(final PlanosRequest request) {
		
		if (StringUtils.isEmpty(request.getNomePlano())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getValor())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}

	}
}
