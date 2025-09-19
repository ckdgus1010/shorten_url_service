package io.changhyun.surl.exception.handler;

import io.changhyun.surl.exception.InvalidUrlException;
import io.changhyun.surl.exception.UrlNotFoundException;
import io.changhyun.surl.exception.UrlShorteningFailureException;
import io.changhyun.surl.exception.UrlStatusNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlShorteningFailureException.class)
    public ResponseEntity<?> handleUrlShorteningFailureException(UrlShorteningFailureException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("URL_SHORTENING_FAILURE", e.getMessage()));
    }

    @ExceptionHandler(UrlStatusNotFoundException.class)
    public ResponseEntity<?> handleUrlStatusNotFoundException(UrlStatusNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("URL_STATUS_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<?> handleInvalidUrlException(InvalidUrlException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_URL", e.getMessage()));
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<?> handleUrlNotFoundException(UrlNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("URL_NOT_FOUND", e.getMessage()));
    }

    private record ErrorResponse(
            String code,
            String message
    ) {
    }
}
