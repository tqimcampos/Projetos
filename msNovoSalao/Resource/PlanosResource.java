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

import com.br.msNovoSalao.Domain.Planos;
import com.br.msNovoSalao.Request.PlanosRequest;
import com.br.msNovoSalao.Request.UpdatePlanoRequest;
import com.br.msNovoSalao.Response.PlanosResponse;
import com.br.msNovoSalao.Service.PlanosService;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.Swagger2Constants;

import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;

@RestController
@RequestMapping(Constants.BASE_PATH)
public class PlanosResource {
	
	private final PlanosService service;

	@Autowired
	public PlanosResource(final PlanosService service) {
		this.service = service;
	}
	
	@PostMapping("/planos")
	public ResponseEntity<PlanosResponse> salvarPlano(

			@ApiParam(required = true, value = Swagger2Constants.MSG_CORPO_REQUISICAO)
			@RequestBody final PlanosRequest request)
			throws NotFoundException {

		return new ResponseEntity<>(this.service.cadastrarPlano(request), HttpStatus.CREATED);
	}
	
	@GetMapping("/planos/{id}")
	public ResponseEntity<PlanosResponse> consultaPlano(
			
			@PathVariable("id") final Long idPlano)
			throws NotFoundException {

		final PlanosResponse response = this.service.findById(idPlano);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/planos/{id}")
	public ResponseEntity<PlanosResponse> alterarPlano(

			@PathVariable("id") final Long idPlano,

			@ApiParam(required = true, value = Swagger2Constants.MSG_CORPO_REQUISICAO)
			@RequestBody(required = true) final UpdatePlanoRequest request)
			throws NotFoundException {

		final PlanosResponse response = this.service.updatePlano(idPlano, request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/planos/{id}")
	public ResponseEntity<Void> inativar(

			@PathVariable("id") final Long idPlano)
			throws NotFoundException {
		this.service.inativar(idPlano);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    @GetMapping("/planos/all")
    public Page<Planos> search(@RequestParam(name = "status", required = false, value = "status")String status,
                                    Pageable pageable) {
              return service.search( pageable,status);

    }
    
	@PutMapping("/planos-ativar/{id}")
	public ResponseEntity<PlanosResponse> ativarPlano(

			@PathVariable("id") final Long idPlano){

		final PlanosResponse response = this.service.ativarPlano(idPlano);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}
