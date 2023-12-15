package com.infocaltechnologies.exceptionhandler.exceptions;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.infocaltechnologies.exceptionhandler.exceptions.SaveStackTrace.saveStackTraceToFile;

@EnableWebMvc
@ControllerAdvice
public class ResourceExceptionHandler{

    @ExceptionHandler({BadRequestException.class, JwtException.class})
    public ResponseModel badRequest(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseModel unauthorized(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
    }

    @ExceptionHandler({PaymentRequiredException.class})
    public ResponseModel paymentRequires(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.PAYMENT_REQUIRED, ex.getLocalizedMessage());
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseModel forbidden(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.FORBIDDEN, ex.getLocalizedMessage());
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseModel objectNotFound(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseModel urlNotFound(Exception ex , HttpServletRequest request) {
        return new ResponseModel(404,
                "A url requisitada não existe. Por favor confira se não houve erro de digitação");
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseModel methodNotAllowed(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage());
    }

    @ExceptionHandler({ProxyAuthException.class})
    public ResponseModel proxyAuthRequired(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, ex.getLocalizedMessage());
    }

    @ExceptionHandler({TimeoutException.class})
    public ResponseModel requestTimeout(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.REQUEST_TIMEOUT, ex.getLocalizedMessage());
    }

    @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class})
    public ResponseModel conflict(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.CONFLICT, ex.getLocalizedMessage());
    }

    @ExceptionHandler({UnsupportedException.class})
    public ResponseModel unsupported(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, DateTimeParseException.class,
            InvalidFormatException.class})
    public ResponseModel invalidAtribute(Exception ex, HttpServletRequest request) {
        String isolatedErrorMessage = getConstraintDefaultMessage(ex);
        return generateResponse(HttpStatus.UNPROCESSABLE_ENTITY, isolatedErrorMessage);
    }

    @ExceptionHandler({TooManyRequestsException.class})
    public ResponseModel tooManyRequests(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getLocalizedMessage());
    }

    @ExceptionHandler({InternalServerException.class})
    public ResponseModel internalError(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
    }

    @ExceptionHandler({BadGatewayException.class})
    public ResponseModel gerenciaNetException(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.BAD_GATEWAY, ex.getLocalizedMessage());
    }

    @ExceptionHandler({UnavailableException.class})
    public ResponseModel serviceUnavailable(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.SERVICE_UNAVAILABLE, ex.getLocalizedMessage());
    }

    @ExceptionHandler({GatewayTimeoutException.class})
    public ResponseModel gatewayTimeout(Exception ex , HttpServletRequest request) {
        return generateResponse(HttpStatus.GATEWAY_TIMEOUT, ex.getLocalizedMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseModel unexpected(Exception ex, HttpServletRequest request) {
        ResponseModel error = new ResponseModel(500,
                "Ocorreu um erro inesperado, entre em contato com o administrador");
        saveStackTraceToFile(ex);
        error.setData(collectErrorLines(ex));
        return error;
    }

    private String getConstraintDefaultMessage(Exception ex) {
        String errorMessage = ex.getLocalizedMessage();
        String startTag = "]]; default message [";
        String endTag = "]";
        int startIndex = errorMessage.indexOf(startTag) + startTag.length();
        int endIndex = errorMessage.indexOf(endTag, startIndex);
        return errorMessage.substring(startIndex, endIndex);
    }

    private List<String> collectErrorLines(Throwable ex) {
        List<String> errorLines = new ArrayList<>();
        while (ex != null) {
            errorLines.add(ex.getClass().getName() + ": " + ex.getMessage());
            ex = ex.getCause();
        }
        return errorLines;
    }

    private static ResponseModel generateResponse(HttpStatus httpStatus, String message) {
        return new ResponseModel(httpStatus.value(), message);
    }

}
