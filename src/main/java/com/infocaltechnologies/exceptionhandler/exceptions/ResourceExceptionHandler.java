package com.infocaltechnologies.exceptionhandler.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<StandardError> methodNotAllowed(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.METHOD_NOT_ALLOWED.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler({ProxyAuthException.class})
    public ResponseEntity<StandardError> proxyAuthRequired(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.PROXY_AUTHENTICATION_REQUIRED.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.PROXY_AUTHENTICATION_REQUIRED).body(error);
    }

    @ExceptionHandler({TimeoutException.class})
    public ResponseEntity<StandardError> requestTimeout(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.REQUEST_TIMEOUT.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(error);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<StandardError> conflict(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.CONFLICT.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler({UnsupportedException.class})
    public ResponseEntity<StandardError> unsuported(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StandardError> invalidAtribute(Exception ex, HttpServletRequest request) {
        String isolatedErrorMessage = getConstraintDefaultMessage(ex);
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        isolatedErrorMessage, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    private String getConstraintDefaultMessage(Exception ex) {
        String errorMessage = ex.getLocalizedMessage();
        String startTag = "]]; default message [";
        String endTag = "]";
        int startIndex = errorMessage.indexOf(startTag) + startTag.length();
        int endIndex = errorMessage.indexOf(endTag, startIndex);
        return errorMessage.substring(startIndex, endIndex);
    }

    @ExceptionHandler({TooManyRequestsException.class})
    public ResponseEntity<StandardError> tooManyRequests(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.TOO_MANY_REQUESTS.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(error);
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
