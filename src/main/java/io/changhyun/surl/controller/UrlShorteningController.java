package io.changhyun.surl.controller;

import io.changhyun.surl.data.dto.UrlShorteningRequest;
import io.changhyun.surl.data.dto.UrlShorteningResponse;
import io.changhyun.surl.service.UrlShorteningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlShorteningController {

    private final UrlShorteningService urlShorteningService;

    @PostMapping("/urls")
    public ResponseEntity<?> shortenUrl(
            @Valid
            @RequestBody
            UrlShorteningRequest request
    ) {
        UrlShorteningResponse response = urlShorteningService.shortenUrl(request.originalUrl());
        return ResponseEntity.ok(response);
    }
}
