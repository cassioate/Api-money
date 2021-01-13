package com.tessaro.moneyapi.exceptionhandler;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.NotFoundException;

@ControllerAdvice
public class MoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

//	NotFoundException
	@ExceptionHandler({ EmptyResultDataAccessException.class })
//	@ResponseStatus(HttpStatus.NOT_FOUND)  <- é uma maneira de retornar o Status sem precisar ter um retorno no metodo.
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString()
				: ex.toString(); /*
									 * <- Nesse caso nao foi utilizado o ex.getCause().toString(), devido ao
									 * EmptyResulData... já ser uma exceção que é a causa, então nao se torna
									 * necessario o getCause()
									 */
		String mensagemUsuario = messageSource.getMessage("objeto.inexistente", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("objeto.inexistente", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleObjetoNaoEncontrado(NotFoundException ex, WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("variavel.inexistente", null,
				LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		String mensagemDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		String mensagemUsuario = messageSource.getMessage("nao.existe.na.base", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

/////// ---- Métodos ---- ///////

	public List<Erro> criarListaDeErros(BindingResult ex) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : ex.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDev = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDev));
		}
		return erros;
	}

}
