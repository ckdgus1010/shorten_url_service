package io.changhyun.surl.data.dto;

import jakarta.validation.constraints.NotBlank;

public record UrlShorteningRequest(
        @NotBlank
        String originalUrl
) {
}
