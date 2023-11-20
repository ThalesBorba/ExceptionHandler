package com.infocaltechnologies.exceptionhandler.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.infocaltechnologies.exceptionhandler.exceptions.SaveStackTrace.saveStackTraceToFile;


@ControllerAdvice
public class ResourceExceptionHandler{

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<StandardError> badRequest(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.BAD_REQUEST.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<StandardError> unauthorized(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.UNAUTHORIZED.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler({PaymentRequiredException.class})
    public ResponseEntity<StandardError> paymentRequires(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.PAYMENT_REQUIRED.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(error);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<StandardError> forbidden(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.FORBIDDEN.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<StandardError> objectNotFound(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.NOT_FOUND.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException .class})
    public ResponseEntity<StandardError> methodNotAllowed(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.METHOD_NOT_ALLOWED.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler({InternalServerException.class})
    public ResponseEntity<StandardError> internalError(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({BadGatewayException.class})
    public ResponseEntity<StandardError> gerenciaNetException(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.BAD_GATEWAY.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        saveStackTraceToFile(ex);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<StandardError> unexpected(Exception ex, HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Ocorreu um erro inesperado, entre em contato com o administrador", request.getRequestURI());
        saveStackTraceToFile(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
