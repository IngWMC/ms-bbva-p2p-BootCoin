package com.nttdata.bbva.p2pBootCoin.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @ExceptionHandler(ModelNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> manejarModeloException(ModelNotFoundException ex, WebRequest request){
        ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponse> manejarModelBadRequest(BadRequestException ex, WebRequest request){
    	logger.info("ERROR BAD_REQUEST ::: " + ex.getMessage());
        ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(er, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> badRequest(MethodArgumentNotValidException ex, WebRequest request, BindingResult bindingResult){
        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    	logger.info("ERROR BAD_REQUEST ::: " + validationErrors.toString());
        ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), validationErrors.toString(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(er, HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarTodasExcepciones(Exception ex, WebRequest request){
    	logger.info("ERROR INTERNAL_SERVER_ERROR ::: " + ex.getMessage());
        ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
