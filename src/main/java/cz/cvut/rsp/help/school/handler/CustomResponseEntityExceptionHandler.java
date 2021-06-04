package cz.cvut.rsp.help.school.handler;

import cz.cvut.rsp.help.school.dto.ErrorResponseDto;
import cz.cvut.rsp.help.school.exception.EntityAlreadyExistsException;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.exception.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);


    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        LOGGER.error("handleEntityNotFoundException", ex);
        ErrorResponseDto errorDetails = new ErrorResponseDto(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponseDto> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        LOGGER.error("handleThrowable", ex);
        ErrorResponseDto error = new ErrorResponseDto("Server Error", request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorResponseDto> handlePermissionDeniedException(AccessDeniedException ex, WebRequest request) {
        LOGGER.error("handleAccessDeniedException", ex);
        ErrorResponseDto errorDetails = new ErrorResponseDto(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public final ResponseEntity<ErrorResponseDto> handlePermissionDeniedException(PermissionDeniedException ex, WebRequest request) {
        LOGGER.error("handlePermissionDeniedException", ex);
        ErrorResponseDto errorDetails = new ErrorResponseDto(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ErrorResponseDto> handleAuthenticationException(Throwable ex, WebRequest request) {
        LOGGER.error("handleAuthenticationException", ex);
        // this is definitely unsafe to return auth error as is but like who cares, this is a school project)
        ErrorResponseDto errorDetails = new ErrorResponseDto(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ErrorResponseDto> handleAllExceptions(Throwable ex, WebRequest request) {
        LOGGER.error("handleThrowable", ex);
        ErrorResponseDto error = new ErrorResponseDto("Server Error", request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
