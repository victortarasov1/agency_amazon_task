package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.dto.ApiError;
import agency.amazon.tarasov.exception.BasicException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BasicException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleBasicException(BasicException ex) {
        var debugMessages = Optional.ofNullable(ex.getCause()).map(Throwable::getMessage).stream().toList();
        return new ApiError(ex.getMessage(), debugMessages);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiError handleUnexpectedException(Exception ex) {
        var debugMessages = Optional.ofNullable(ex.getCause()).map(Throwable::getMessage).stream().toList();
        return new ApiError(ex.getMessage(), debugMessages);
    }
}
