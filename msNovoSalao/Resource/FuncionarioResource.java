package com.br.msNovoSalao.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.msNovoSalao.Domain.Funcionario;
import com.br.msNovoSalao.Request.FuncionarioRequest;
import com.br.msNovoSalao.Request.UpdateFuncionarioRequest;
import com.br.msNovoSalao.Response.FuncionarioResponse;
import com.br.msNovoSalao.Service.FuncionarioService;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.Swagger2Constants;

import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;

@RestController
@RequestMapping(Constants.BASE_PATH)
public class FuncionarioResource {
	
	private final FuncionarioService service;

	@Autowired
	public FuncionarioResource(final FuncionarioService service) {
		this.service = service;
	}
	
	@PostMapping("/funcionario")
	public ResponseEntity<FuncionarioResponse> salvarFuncionario(

			@ApiParam(required = true, value = Swagger2Constants.MSG_CORPO_REQUISICAO)
			@RequestBody final FuncionarioRequest request)
			throws NotFoundException {

		return new ResponseEntity<>(this.service.cadastrarFuncionario(request), HttpStatus.CREATED);
	}
	
	@GetMapping("/funcionario/{id}")
	public ResponseEntity<FuncionarioResponse> consultaFuncionario(

			@PathVariable("id") final Long idFuncionario)
			throws NotFoundException {

		final FuncionarioResponse response = this.service.findById(idFuncionario);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/funcionario/{id}")
	public ResponseEntity<FuncionarioResponse> alterar(
			
			@PathVariable("id") final Long idFuncionario,

			@ApiParam(required = true, value = Swagger2Constants.MSG_CORPO_REQUISICAO)
			@RequestBody(required = true) final UpdateFuncionarioRequest request)
			throws NotFoundException {

		final FuncionarioResponse response = this.service.updateFuncionario(idFuncionario, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @GetMapping("/funcionario/all")
    public Page<Funcionario> search(@RequestParam(name = "status", required = false, value = "status")String status,
                                    Pageable pageable) {
              return service.search( pageable,status);

    }
    
    
	@DeleteMapping("/funcionario/{id}")
	public ResponseEntity<Void> inativar(
			
			@PathVariable("id") final Long idFuncionario)
			throws NotFoundException {
		this.service.inativar(idFuncionario);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
