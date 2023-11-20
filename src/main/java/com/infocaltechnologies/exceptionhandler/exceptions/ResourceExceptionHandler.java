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
        return generateResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<StandardError> unauthorized(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({PaymentRequiredException.class})
    public ResponseEntity<StandardError> paymentRequires(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.PAYMENT_REQUIRED, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<StandardError> forbidden(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<StandardError> objectNotFound(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<StandardError> methodNotAllowed(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({ProxyAuthException.class})
    public ResponseEntity<StandardError> proxyAuthRequired(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({TimeoutException.class})
    public ResponseEntity<StandardError> requestTimeout(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.REQUEST_TIMEOUT, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<StandardError> conflict(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.CONFLICT, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({UnsupportedException.class})
    public ResponseEntity<StandardError> unsuported(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StandardError> invalidAtribute(Exception ex, HttpServletRequest request) {
        String isolatedErrorMessage = getConstraintDefaultMessage(ex);
        return generateResponse(HttpStatus.UNPROCESSABLE_ENTITY, isolatedErrorMessage, request);
    }

    @ExceptionHandler({TooManyRequestsException.class})
    public ResponseEntity<StandardError> tooManyRequests(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({InternalServerException.class})
    public ResponseEntity<StandardError> internalError(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({BadGatewayException.class})
    public ResponseEntity<StandardError> gerenciaNetException(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.BAD_GATEWAY, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({UnavailableException.class})
    public ResponseEntity<StandardError> serviceUnavailable(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.SERVICE_UNAVAILABLE, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({GatewayTimeoutException.class})
    public ResponseEntity<StandardError> gatewayTimeout(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.GATEWAY_TIMEOUT, ex.getLocalizedMessage(), request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<StandardError> unexpected(Exception ex, HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Ocorreu um erro inesperado, entre em contato com o administrador", request.getRequestURI());
        saveStackTraceToFile(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String getConstraintDefaultMessage(Exception ex) {
        String errorMessage = ex.getLocalizedMessage();
        String startTag = "]]; default message [";
        String endTag = "]";
        int startIndex = errorMessage.indexOf(startTag) + startTag.length();
        int endIndex = errorMessage.indexOf(endTag, startIndex);
        return errorMessage.substring(startIndex, endIndex);
    }

    private static ResponseEntity<StandardError> generateResponse(HttpStatus httpStatus, String ex,
                                                                  HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(ZoneId.of("UTC")), httpStatus.value(),
                        ex, request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(error);
    }

}
