package io.changhyun.surl.data.dto;

import java.time.Instant;

public record UrlShorteningResponse(
        String result,
        Instant expiredAt
) {
}
