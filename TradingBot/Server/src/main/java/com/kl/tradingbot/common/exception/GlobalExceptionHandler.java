package com.kl.tradingbot.common.exception;

import com.kl.tradingbot.common.exception.model.ErrorMessageEnum;
import com.kl.tradingbot.common.exception.model.response.ExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex,
      WebRequest request) {
    String message = ex.getMessage();
    ExceptionResponse exceptionResponse = new ExceptionResponse(message,
        request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(TradingBotException.class)
  public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
        ex.getBindingResult().toString());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String errorMessage = ex.getBindingResult().getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .findFirst()
        .orElse(ErrorMessageEnum.VALIDATION_ERROR.getMessage());

    ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage,
        ex.getBindingResult().toString());
    System.out.println();
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

}