package io.changhyun.surl.service;

import io.changhyun.surl.component.RandomCodeGenerator;
import io.changhyun.surl.data.domain.ShortenedUrl;
import io.changhyun.surl.data.dto.UrlShorteningResponse;
import io.changhyun.surl.exception.UrlShorteningFailureException;
import io.changhyun.surl.repository.ShortenedUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlShorteningService {

    private final ShortenedUrlRepository shortenedUrlRepository;
    private final RandomCodeGenerator randomCodeGenerator;

    private static final int DEFAULT_URL_EXPIRATION_PERIOD = 7;

    @Transactional
    public UrlShorteningResponse shortenUrl(String originalUrl) {
        try {
            String code = randomCodeGenerator.generateRandomCode();
            ShortenedUrl newEntity = new ShortenedUrl(originalUrl, code, DEFAULT_URL_EXPIRATION_PERIOD);

            ShortenedUrl saved = shortenedUrlRepository.save(newEntity);

            return new UrlShorteningResponse(saved.getCode(), saved.getExpiredAt());
        } catch (DataIntegrityViolationException exception) {
            throw new UrlShorteningFailureException("URL을 단축시킬 수 없습니다. 잠시 후 다시 시도해 주세요.");
        }
    }
}
