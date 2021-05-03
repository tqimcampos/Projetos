package com.br.msNovoSalao.Resource;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.msNovoSalao.Domain.ServicoFuncionario;
import com.br.msNovoSalao.Request.ServicoFuncionarioRequest;
import com.br.msNovoSalao.Response.ServicoFuncionarioResponse;
import com.br.msNovoSalao.Response.SubTotalResponse;
import com.br.msNovoSalao.Service.ServicoFuncionarioService;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.Swagger2Constants;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(Constants.BASE_PATH)
public class ServicoFuncionarioResource {


	private final ServicoFuncionarioService service;

	@Autowired
	public ServicoFuncionarioResource(final ServicoFuncionarioService service) {
		this.service = service;
	}
	
	
	@PostMapping("/servicos-funcionarios")
	public ResponseEntity<ServicoFuncionarioResponse> salvar(

			@ApiParam(required = true, value = Swagger2Constants.MSG_CORPO_REQUISICAO)
			@RequestBody final ServicoFuncionarioRequest request)
{

		return new ResponseEntity<>(this.service.cadastrar(request), HttpStatus.CREATED);
	}
	
	
	   @GetMapping("/servicos-funcionarios/all")
	    public Page<ServicoFuncionario> getAll(Pageable pageable) {
	              return service.getAll( pageable);

	    }
	   
		@GetMapping("/servicos-funcionarios/{id}")
		public ResponseEntity<ServicoFuncionarioResponse> consultarServicoFuncionario(

				@PathVariable("id") final Long id){

			final ServicoFuncionarioResponse response = this.service.findById(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		@PutMapping("/servicos-funcionarios/{id}")
		public ResponseEntity<ServicoFuncionarioResponse> alterar(
				
				@PathVariable("id") final Long id,

				@RequestBody(required = true) final ServicoFuncionarioRequest request){

			final ServicoFuncionarioResponse response = this.service.updateServico(id, request);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		@GetMapping("/servicos-funcionarios/all/{cpf}")
		public ResponseEntity<SubTotalResponse> consultarServicosFuncionario(
				
				@RequestParam(name = "dataInicial", required = false, value = "dataInicial")String dataInicial, 
				@RequestParam(name = "dataFinal", required = false, value = "dataFinal")String dataFinal,
				@PathVariable("cpf") final String cpf){
			
			Timestamp dataInicio = Timestamp.valueOf(dataInicial);
			Timestamp dataFim = Timestamp.valueOf(dataFinal);
			final SubTotalResponse dadosResponse = this.service.servicosFuncionario(cpf,dataInicio,dataFim);
			return new ResponseEntity<>(dadosResponse, HttpStatus.OK);
		}
		
}
