package com.br.msNovoSalao.Service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.br.msNovoSalao.Domain.Funcionario;
import com.br.msNovoSalao.Domain.Usuario;
import com.br.msNovoSalao.Exceptions.ErrorCodes;
import com.br.msNovoSalao.Exceptions.PreconditionFailedException;
import com.br.msNovoSalao.Repository.FuncionarioRepository;
import com.br.msNovoSalao.Repository.UsuarioRepository;
import com.br.msNovoSalao.Request.FuncionarioRequest;
import com.br.msNovoSalao.Request.UpdateFuncionarioRequest;
import com.br.msNovoSalao.Response.FuncionarioResponse;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.ConverterUtil;

@Lazy
@Service
public class FuncionarioService {
	

	private final FuncionarioRepository repository;
	private final UsuarioRepository usuarioRepository;

	@Autowired
	public FuncionarioService(FuncionarioRepository repository, UsuarioRepository usuarioRepository){
		this.repository = repository;
		this.usuarioRepository = usuarioRepository;
	}

	public FuncionarioResponse cadastrarFuncionario(final FuncionarioRequest request) {
		
		this.validarRequestFuncionario(request);
				
		Funcionario func = this.repository.findByCpf(request.getCpf());
		if (!StringUtils.isEmpty(func)) {
		       throw new PreconditionFailedException( "Já existe um funcionário cadastrado com esse cpf!");
		}
		
		Usuario usu = this.usuarioRepository.findByEmail(request.getEmail());
		if (!StringUtils.isEmpty(usu)) {
		       throw new PreconditionFailedException( "O email informado, ja possui cadastro!");
		}
		
		Funcionario funcionario = new Funcionario();
				
		convertRequestFuncionarioToDomain(request, funcionario);

		Usuario usuario = new Usuario();
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(request.getSenha());
		usuario.setStatus(Constants.ATIVO);
		usuario.setTipoUsuario(Constants.TIPO_USUARIO);
		usuario.setDataCadastro(ConverterUtil.nowTime());
		
		Funcionario domain = this.repository.save(funcionario);
		this.usuarioRepository.save(usuario);
		domain.setIdUsuario(usuario.getId());
		this.repository.save(domain);

		FuncionarioResponse response = converterDomainToResponseFuncionario(domain);

		return response;
	}

	public FuncionarioResponse findById(final Long idFuncionario) {

		final Funcionario funcionario = this.repository.findByIdFuncionario(idFuncionario);
		if (Objects.isNull(funcionario)) {
		       throw new PreconditionFailedException( "Id funcionário não existe");

		}
		
		FuncionarioResponse response = converterDomainToResponseFuncionario(funcionario);

		return response;
	}
	
	public FuncionarioResponse updateFuncionario(final Long idFuncionario, final UpdateFuncionarioRequest request){

		final Funcionario domain = this.repository.findByIdFuncionario(idFuncionario);
		
		if (Objects.isNull(domain)) {
		       throw new PreconditionFailedException( "Id funcionário não existe");
		}
		
		validateRquestUpdate(request, domain);
		
		domain.setDataAlteracao(ConverterUtil.nowTime());
		
		this.repository.save(domain);

		final Usuario usuario = this.usuarioRepository.findById(domain.getIdUsuario());
		
		if(!StringUtils.isEmpty(request.getNome())) {
			usuario.setNome(request.getNome());
		}		
		
		if(!StringUtils.isEmpty(request.getSenha())) {
			usuario.setSenha(request.getSenha());
		}
		
		if(!StringUtils.isEmpty(request.getEmail())) {
			usuario.setEmail(request.getEmail());
		}
		
		usuario.setDataAlteracao(ConverterUtil.nowTime());
			
		this.usuarioRepository.save(usuario);
		
		FuncionarioResponse response = converterDomainToResponseFuncionario(domain);

		return response;
	}
	
    public Page<Funcionario> search(Pageable pageable, String status) {
       
        return repository.search(
       		pageable, status);
    }
	
	public void inativar(final Long idFuncionario){
		
		final Funcionario funcionarioDomain = this.repository.findByIdFuncionario(idFuncionario);
		
		if (Objects.isNull(funcionarioDomain)) {
		       throw new PreconditionFailedException( "Id funcionário não existe");
		}
		funcionarioDomain.setStatus(Constants.INATIVO);
		funcionarioDomain.setDataAlteracao(ConverterUtil.nowTime());
		
	    this.repository.save(funcionarioDomain);
	    
		final Usuario usuarioDomain = this.usuarioRepository.findById(funcionarioDomain.getIdUsuario());
		usuarioDomain.setStatus(Constants.INATIVO);
		usuarioDomain.setDataAlteracao(ConverterUtil.nowTime());

	    this.usuarioRepository.save(usuarioDomain);
	}

	private void validarRequestFuncionario(final FuncionarioRequest request) {
		
		if (StringUtils.isEmpty(request.getNome())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getTipoFuncionario())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getEmail())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getCelular())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		
		
	}
	
	private FuncionarioResponse converterDomainToResponseFuncionario(Funcionario domain) {

		FuncionarioResponse response = new FuncionarioResponse();
		response.setIdFuncionario(domain.getIdFuncionario());
		response.setIdUsuario(domain.getIdUsuario());
		response.setNome(domain.getNome());
		response.setDataNascimento(domain.getDataNascimento());
		response.setSexo(domain.getSexo());
		response.setCpf(domain.getCpf());
		response.setRg(domain.getRg());
		response.setCelular(domain.getCelular());
		response.setTipoFuncionario(domain.getTipoFuncionario());
		response.setEstado(domain.getEstado());
		response.setMunicipio(domain.getMunicipio());
		response.setBairro(domain.getBairro());
		response.setCep(domain.getCep());
		response.setRua(domain.getRua());
		response.setNumero(domain.getNumero());
		response.setComplemento(domain.getComplemento());
		response.setEmail(domain.getEmail());
		response.setStatus(domain.getStatus());
		response.setDataCadastro(domain.getDataCadastro());
		response.setDataAlteracao(domain.getDataAlteracao());

		return response;
	}

	private void convertRequestFuncionarioToDomain(final FuncionarioRequest request, Funcionario funcionario) {
		funcionario.setNome(request.getNome());
		funcionario.setTipoFuncionario(request.getTipoFuncionario());
		funcionario.setEmail(request.getEmail());
		funcionario.setDataNascimento(request.getDataNascimento());
		funcionario.setCpf(request.getCpf());
		funcionario.setRg(request.getRg());
		funcionario.setSexo(request.getSexo());
		funcionario.setEstado(request.getEstado());
		funcionario.setMunicipio(request.getMunicipio());
		funcionario.setBairro(request.getBairro());
		funcionario.setCep(request.getCep());
		funcionario.setRua(request.getRua());
		funcionario.setNumero(request.getNumero());
		funcionario.setComplemento(request.getComplemento());
		funcionario.setCelular(request.getCelular());
		funcionario.setStatus(Constants.ATIVO);
		funcionario.setDataCadastro(ConverterUtil.nowTime());
	}
	
	private void validateRquestUpdate(final UpdateFuncionarioRequest request, final Funcionario domain) {
		if(!StringUtils.isEmpty(request.getNome())) {
			domain.setNome(request.getNome());
		}
		
		if(!StringUtils.isEmpty(request.getEmail())) {
			domain.setEmail(request.getEmail());
		}
		
		if(!StringUtils.isEmpty(request.getTipoFuncionario())) {
			domain.setTipoFuncionario(request.getTipoFuncionario());
		}
		
		if(!StringUtils.isEmpty(request.getDataNascimento())) {
			domain.setDataNascimento(request.getDataNascimento());
		}
		
		if(!StringUtils.isEmpty(request.getRg())) {
			domain.setRg(request.getRg());
		}
		
		if(!StringUtils.isEmpty(request.getSexo())) {
			domain.setSexo(request.getSexo());
		}
		
		if(!StringUtils.isEmpty(request.getCelular())) {
			domain.setCpf(request.getCpf());
		}
		
		if(!StringUtils.isEmpty(request.getEstado())) {
			domain.setEstado(request.getEstado());
		}
		
		if(!StringUtils.isEmpty(request.getMunicipio())) {
			domain.setMunicipio(request.getMunicipio());
		}
		
		if(!StringUtils.isEmpty(request.getBairro())) {
			domain.setBairro(request.getBairro());
		}
		
		if(!StringUtils.isEmpty(request.getCep())) {
			domain.setCep(request.getCep());
		}
		
		if(!StringUtils.isEmpty(request.getRua())) {
			domain.setRua(request.getRua());
		}
		if(!StringUtils.isEmpty(request.getNumero())) {
			domain.setNumero(request.getNumero());
		}
		
		if(!StringUtils.isEmpty(request.getStatus())) {
			domain.setStatus(request.getStatus());
		}
	}
}
