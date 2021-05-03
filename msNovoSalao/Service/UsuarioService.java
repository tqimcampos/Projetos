package com.br.msNovoSalao.Service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.br.msNovoSalao.Domain.Usuario;
import com.br.msNovoSalao.Exceptions.ErrorCodes;
import com.br.msNovoSalao.Exceptions.PreconditionFailedException;
import com.br.msNovoSalao.Repository.UsuarioRepository;
import com.br.msNovoSalao.Request.LoginRequest;
import com.br.msNovoSalao.Request.UpdateUsuarioRequest;
import com.br.msNovoSalao.Request.UsuarioRequest;
import com.br.msNovoSalao.Response.UsuarioResponse;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.ConverterUtil;

@Lazy
@Service
public class UsuarioService {
	
	private final UsuarioRepository repository;
	
	@Autowired
	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public UsuarioResponse cadastrarUsuario(final UsuarioRequest request) {

		this.validarUsuario(request);

		Usuario user = this.repository.findByEmail(request.getEmail());
		if (!StringUtils.isEmpty(user)) {
		       throw new PreconditionFailedException( "Já existe um usuario cadastrado neste email!");
		}
		
		Usuario usuarioDomain = new Usuario();
				
		convertRequestUsuarioToDomain(request, usuarioDomain);
		
		Usuario domain = this.repository.save(usuarioDomain);
				
		UsuarioResponse response = converterDomainToResponseUsuario(domain);

		return response;

	}
	
	public UsuarioResponse findById(final Long id) {

		final Usuario usuario = this.repository.findById(id);

		UsuarioResponse response = new UsuarioResponse();

		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		
		return response;
	}
	
	public UsuarioResponse usuarioLogin(final LoginRequest request) {
		
		this.validarLogin(request);
		
		
		//buscando usuario por email
		Usuario usuario = repository.findByEmail(request.getEmail());
		if (StringUtils.isEmpty(usuario))  {
			throw new PreconditionFailedException("Usuario não existe!");
		}
		
		// verificando se a senha passada no request é igual a senha que foi cadastrada no usuario em questao
		Boolean retorno = request.getSenha().equals(usuario.getSenha());

		if (retorno == false)  {
			throw new PreconditionFailedException("Senha incorreta!");
		}

		UsuarioResponse response = new UsuarioResponse();
		response.setEmail(usuario.getEmail());
		response.setNome(usuario.getNome());
		response.setStatus(usuario.getStatus());
		response.setUltimoLogin(usuario.getUltimoLoguin());
		response.setTipoUsuario(usuario.getTipoUsuario());
		
		return response;
		
	}
	
	public UsuarioResponse updateUsuario(final Long id, final UpdateUsuarioRequest request){

		final Usuario domain = this.repository.findById(id);
		
		if (Objects.isNull(domain)) {
		       throw new PreconditionFailedException( "Id usuario não existente");
		}
		
		validateRquestUpdate(request, domain);
		
		domain.setDataAlteracao(ConverterUtil.nowTime());
		this.repository.save(domain);

		UsuarioResponse response = converterDomainToResponseUsuario(domain);

		return response;
	}
	
	private void convertRequestUsuarioToDomain(final UsuarioRequest request, Usuario usuario) {
		usuario.setId(request.getId());
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(request.getSenha());
		usuario.setStatus(Constants.ATIVO);
		usuario.setTipoUsuario(Constants.TIPO_USER);
		usuario.setDataCadastro(ConverterUtil.nowTime());
	}
	
	private UsuarioResponse converterDomainToResponseUsuario(Usuario domain) {

		UsuarioResponse response = new UsuarioResponse();
		response.setId(domain.getId());
		response.setNome(domain.getNome());
		response.setTipoUsuario(domain.getTipoUsuario());
		response.setEmail(domain.getEmail());
		response.setStatus(domain.getStatus());
		response.setDataCadastro(domain.getDataCadastro());
		response.setDataAlteracao(domain.getDataAlteracao());

		return response;
	}
	
	private void validarUsuario(final UsuarioRequest request) {
		
		if (StringUtils.isEmpty(request.getNome())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getEmail())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}	
		if (StringUtils.isEmpty(request.getSenha())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		
	}
	
	private void validateRquestUpdate(final UpdateUsuarioRequest request, final Usuario domain) {
		
		if(!StringUtils.isEmpty(request.getNome())) {
			domain.setNome(request.getNome());
		}
		if(!StringUtils.isEmpty(request.getEmail())) {
			domain.setEmail(request.getEmail());
		}
		if(!StringUtils.isEmpty(request.getSenha())) {
			domain.setSenha(request.getSenha());
		}
		if(!StringUtils.isEmpty(request.getTipoUsuario())) {
			domain.setTipoUsuario(request.getTipoUsuario());
		}
		
	}

	
	private void validarLogin(final LoginRequest request) {
		
		if (StringUtils.isEmpty(request.getEmail())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
		if (StringUtils.isEmpty(request.getSenha())) {
			throw new PreconditionFailedException(ErrorCodes.VALIDATE_CAMPO_OBRIGATORIO);
		}
	
	}

}
