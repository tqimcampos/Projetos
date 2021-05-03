/*
* Copyright 2018 Hapvida
*************************************************************
*Nome     : Loggable.java
*Autor    : javackson
*Data     : 14/10/2018
*Empresa  : Next Digital Platform
*************************************************************
*/
package com.br.msNovoSalao.Utils.Anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
}