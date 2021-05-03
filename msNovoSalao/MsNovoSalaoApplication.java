package com.br.msNovoSalao;

import java.sql.Timestamp;
import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.br.msNovoSalao.Response.VersionMavenResponse;
import com.br.msNovoSalao.Utils.Constants;
import com.br.msNovoSalao.Utils.SSLUtils;
import com.br.msNovoSalao.Utils.Anotacoes.Loggable;
import com.fasterxml.jackson.core.JsonParser.Feature;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@ApiIgnore
@SpringBootApplication
public class MsNovoSalaoApplication extends SpringBootServletInitializer {

	  @Autowired
	  private BuildProperties build;

	  public static void main(final String[] args) {
	    SpringApplication.run(MsNovoSalaoApplication.class, args);
	    
	  }

	  @Bean
	  public ModelMapper buildModelMapper() {
	    final ModelMapper modelMapper = new ModelMapper();
	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	    return modelMapper;
	  }

	@Bean
	  public Jackson2ObjectMapperBuilder buildJacksonObjectMapper() {
	    final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
	    builder.timeZone(TimeZone.getDefault());
	    builder.featuresToEnable(Feature.ALLOW_UNQUOTED_CONTROL_CHARS);

	    return builder;
	  }

	  @Bean
	  public RestTemplate restTemplate() {
	    return new RestTemplate( SSLUtils.buildHttpRequestFactory() );
	  }

	  @Loggable
	  @GetMapping(Constants.BASE_PATH + "/version")
	  public ResponseEntity<VersionMavenResponse> version() {
	    final VersionMavenResponse version = new VersionMavenResponse();
	    version.setName(this.build.getName());
	    version.setVersion(this.build.getVersion());
	    version.setBuild(new Timestamp(this.build.getTime().getTime()));
	    version.setNow(new Timestamp(System.currentTimeMillis()));

	    return new ResponseEntity<>(version, HttpStatus.OK);
	  }

}
