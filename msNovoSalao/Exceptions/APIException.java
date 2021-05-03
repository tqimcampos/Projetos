package com.br.msNovoSalao.Exceptions;

import org.modelmapper.spi.ErrorMessage;

import lombok.Getter;

@Getter
public abstract class APIException extends RuntimeException {

private static final long serialVersionUID = 1L;

private final ErrorMessage error;

  public APIException(final Throwable cause) {
    super(cause);
    this.error = new ErrorMessage(cause.getMessage());
  }

  public APIException(final ErrorMessage error) {
    this.error = error;
  }

  public APIException(final String error) {
    this.error = new ErrorMessage(error);
  }

}
