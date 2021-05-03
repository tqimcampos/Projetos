/*
* Copyright 2019 Hapvida
*************************************************************
*Name    : SwaggerConfig.java
*Date    : 27/03/2019
*System  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig implements Serializable{
  private static final long serialVersionUID = 1L;

  private final transient BuildProperties build;

  @Value("${host}")
  private String url;
  
  @Autowired
  public SwaggerConfig( final BuildProperties build ) {
    this.build = build;
  }
  
  @Bean
  public Docket api() {
    return new Docket( DocumentationType.SWAGGER_2 )
                  .useDefaultResponseMessages( false )
                  .host(this.url )
                  .select()
                  .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")) )
                  .build()
        .apiInfo( this.buildMetaDataDocumentation() );
  }

  private ApiInfo buildMetaDataDocumentation() {
    return new ApiInfoBuilder().title( "[CARTAO BRASIL SAUDE]" )
                               .description( "Serviços de saúde" )
                               .version( this.build.getVersion() )
                               .license(" Todos os direitos reservados")
                               .build();
  }
}

