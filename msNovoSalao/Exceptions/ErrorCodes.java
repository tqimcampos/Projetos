package com.br.msNovoSalao.Exceptions;

public interface ErrorCodes {

	String REQUIRED_FIELD_NOT_INFORMED = "requiredField.not.informed";

	String VALIDATE_FIELD_MAX_LENGTH = "validateField.max.length";

	String VALIDATE_FIELD_MIN_LENGTH = "validateField.min.length";

	String VALIDATE_FIELD_INVALID_RANGE = "validateField.invalid.length";

	String VALIDATE_RECORD_ALREADY_REGISTERED = "validateRecord.already.registered";

	String VALIDATE_FIELD_INVALID_PATTERN = "validateField.invalid.pattern";

	public static final String VALIDATE_NOT_FOUND = "nao.encontrado.na.base";

	public static final String VALIDATE_FUNCIONARIO_JA_CADASTRADO = "funcionario.ja.cadastrado";

	public static final String VALIDATE_CAMPO_OBRIGATORIO = "campo.obrigatorio";

	public static final String VALIDATE_EMAIL_INEXISTENTE = "email.não.encontrado";

	public static final String VALIDATE_SENHA_INCORRETA = "senha.incorreta";
	
	String VALIDATE_ID_NAO_ENCONTRADO = "id.nao.encontrado";


	// Others
}
