package com.br.msNovoSalao.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.msNovoSalao.Request.LoginRequest;
import com.br.msNovoSalao.Request.UpdateUsuarioRequest;
import com.br.msNovoSalao.Request.UsuarioRequest;
import com.br.msNovoSalao.Response.UsuarioResponse;
import com.br.msNovoSalao.Service.UsuarioService;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.Swagger2Constants;

import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;

@RestController
@RequestMapping(Constants.BASE_PATH)
public class UsuarioResource {
	
	private final UsuarioService service;

	@Autowired
	public UsuarioResource(final UsuarioService service) {
		this.service = service;
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<UsuarioResponse> salvarUsuario(

			@ApiParam(required = true, value = Swagger2Constants.MSG_CORPO_REQUISICAO)
			@RequestBody final UsuarioRequest request)
			throws NotFoundException {

		return new ResponseEntity<>(this.service.cadastrarUsuario(request), HttpStatus.CREATED);
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioResponse> consultaUsuario(
			@PathVariable("id") final Long id)
			throws NotFoundException {

		final UsuarioResponse response = this.service.findById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/login")
    public ResponseEntity<UsuarioResponse> loginUsuario(
		@RequestBody final LoginRequest request){

	final UsuarioResponse response = this.service.usuarioLogin(request);
	return new ResponseEntity<>(response, HttpStatus.OK);
	
	}
	
	@PutMapping("/usuario/{id}")
	public ResponseEntity<UsuarioResponse> alterarUsuario(

			@PathVariable("id") final Long id,

			@RequestBody(required = true) final UpdateUsuarioRequest request){

		final UsuarioResponse response = this.service.updateUsuario(id, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
		

}
