package com.abinarystar.core.web;

import com.abinarystar.core.validation.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

  @ExceptionHandler(exception = ValidationException.class, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Response<Void>> validationException(HttpServletRequest request, ValidationException ex) {
    logWarn("Validation | path: {} {} | ex: {}", request, ex);
    return ResponseEntity.badRequest()
        .body(Response.error(ex.getCode(), ex.getErrors()));
  }

  @ExceptionHandler({
      ConversionNotSupportedException.class,
      HandlerMethodValidationException.class,
      HttpMessageNotReadableException.class,
      HttpMessageNotWritableException.class,
      MethodArgumentNotValidException.class,
      MethodValidationException.class,
      MissingPathVariableException.class,
      MissingServletRequestParameterException.class,
      MissingServletRequestPartException.class,
      ServletRequestBindingException.class,
      TypeMismatchException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void badRequestException(HttpServletRequest request, Exception ex) {
    logWarn("Bad request | path: {} {} | ex: {}", request, ex);
  }

  @ExceptionHandler({
      NoHandlerFoundException.class,
      NoResourceFoundException.class
  })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void notFoundException(HttpServletRequest request, Exception ex) {
    logWarn("Not found | path: {} {} | ex: {}", request, ex);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public void methodNotAllowedException(HttpServletRequest request, Exception ex) {
    logWarn("Method not allowed | path: {} {} | ex: {}", request, ex);
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  public void notAcceptableException(HttpServletRequest request, Exception ex) {
    logWarn("Not acceptable | path: {} {} | ex: {}", request, ex);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public void dataIntegrityViolationException(HttpServletRequest request, Exception ex) {
    logWarn("Data integrity violation | path: {} {} | ex: {}", request, ex);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  public void unsupportedMediaTypeException(HttpServletRequest request, Exception ex) {
    logWarn("Unsupported media type | path: {} {} | ex: {}", request, ex);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus
  public void unknownException(HttpServletRequest request, Exception ex) {
    logError("Unknown | path: {} {} | ex: {}", request, ex);
  }

  @ExceptionHandler(AsyncRequestTimeoutException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public void asyncRequestTimeoutException(HttpServletRequest request, Exception ex) {
    logWarn("Async request timeout | path: {} {} | ex: {}", request, ex);
  }

  private void logWarn(String format, HttpServletRequest request, Exception ex) {
    log.warn(format, request.getMethod(), request.getRequestURI(), ex.getMessage());
  }

  private void logError(String format, HttpServletRequest request, Exception ex) {
    log.error(format, request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
  }
}
