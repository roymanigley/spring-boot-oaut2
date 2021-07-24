package ch.bytecrowd.securitydemo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler
    ResponseEntity<Object> handleError(Exception e, WebRequest request) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorBody(e.getMessage()));
    }

    static class ErrorBody {
        private final String message;

        public ErrorBody(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
