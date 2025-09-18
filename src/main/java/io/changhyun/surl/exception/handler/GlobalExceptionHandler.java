package io.changhyun.surl.exception.handler;

import io.changhyun.surl.exception.UrlShorteningFailureException;
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

    private record ErrorResponse(
            String code,
            String message
    ) {
    }
}
