package io.changhyun.surl.controller;

import io.changhyun.surl.exception.InvalidUrlException;
import io.changhyun.surl.service.UrlReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UrlCallController {

    private final UrlReadService urlReadService;

    @GetMapping("/{code}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String code) {
        String originalUrl = urlReadService.findOriginalUrl(code);

        try {
            URI uri = new URI(originalUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uri);

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .headers(headers)
                    .build();
        } catch (URISyntaxException e) {
            log.error("유효한 형식의 URL이 아닙니다. code = {}, originalUrl = {}", code, originalUrl);
            throw new InvalidUrlException("유효한 형식의 URL이 아닙니다.");
        }
    }
}
