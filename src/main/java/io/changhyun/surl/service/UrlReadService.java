package io.changhyun.surl.service;

import io.changhyun.surl.data.domain.ShortenedUrl;
import io.changhyun.surl.enums.UrlStatus;
import io.changhyun.surl.exception.UrlNotFoundException;
import io.changhyun.surl.repository.ShortenedUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlReadService {

    private final ShortenedUrlRepository shortenedUrlRepository;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String REDIS_PREFIX = "surl:code:";
    private static final String REDIS_NULL_VALUE = "__NULL__";

    @Transactional(readOnly = true)
    public String findOriginalUrl(String code) {
        long start = System.nanoTime();

        // Redis 조회
        Optional<String> optional = readFromRedis(code);
        
        if (optional.isPresent()) {
            long duration = System.nanoTime() - start;
            String msg = String.format("Find from Redis: %.3fms", duration * 0.000001);
            log.info(msg);

            return optional.get();
        }

        ShortenedUrl entity = shortenedUrlRepository.findByCode(code)
                .orElseThrow(() -> {
                    log.warn("저장된 Original URL을 찾을 수 없습니다. code = {}", code);
                    return new UrlNotFoundException("URL을 찾을 수 없습니다.");
                });

        long duration = System.nanoTime() - start;
        String msg = String.format("Find from DB: %.3fms", duration * 0.000001);
        log.info(msg);

        String status = entity.getStatus().getStatusName();

        if (status.equals(UrlStatus.ACTIVE.getLabel())) {
            // Redis에 저장
            saveToRedis(code, entity.getOriginalUrl(), entity.getExpiredAt());

            return entity.getOriginalUrl();
        } else {
            // TODO: 실패 화면으로 redirect
            return null;
        }
    }

    private Optional<String> readFromRedis(String code) {
        String key = REDIS_PREFIX + code;
        String cached = stringRedisTemplate.opsForValue().get(key);

        if (cached == null || cached.equals(REDIS_NULL_VALUE)) {
            return Optional.empty();
        }

        return Optional.of(cached);
    }

    private void saveToRedis(String code, String url, Instant expiredAt) {
        String key = REDIS_PREFIX + code;
        Duration ttl = Duration.between(Instant.now(), expiredAt);

        stringRedisTemplate.opsForValue().set(key, url, ttl);
    }
}
