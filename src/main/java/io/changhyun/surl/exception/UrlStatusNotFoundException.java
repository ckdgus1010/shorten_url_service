package io.changhyun.surl.exception;

public class UrlStatusNotFoundException extends RuntimeException {
    public UrlStatusNotFoundException(String message) {
        super(message);
    }
}
