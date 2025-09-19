package io.changhyun.surl.service;

import io.changhyun.surl.component.RandomCodeGenerator;
import io.changhyun.surl.data.domain.ShortenedUrl;
import io.changhyun.surl.data.domain.ShortenedUrlStatus;
import io.changhyun.surl.data.dto.UrlShorteningResponse;
import io.changhyun.surl.exception.InvalidUrlException;
import io.changhyun.surl.exception.UrlNotFoundException;
import io.changhyun.surl.exception.UrlShorteningFailureException;
import io.changhyun.surl.exception.UrlStatusNotFoundException;
import io.changhyun.surl.repository.ShortenedUrlRepository;
import io.changhyun.surl.repository.ShortenedUrlStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlShorteningService {

    private final ShortenedUrlRepository shortenedUrlRepository;
    private final ShortenedUrlStatusRepository shortenedUrlStatusRepository;

    private static final UrlValidator urlValidator = new UrlValidator();
    private final RandomCodeGenerator randomCodeGenerator;

    @Value("${template.domain}")
    private String domain;

    private static final Pattern REGEX_HTTP_PREFIX = Pattern.compile("^(http|https)://.+$");
    private static final int DEFAULT_URL_EXPIRATION_PERIOD = 7;
    private static final long DEFAULT_STATUS_ID = 1;

    @Transactional
    public UrlShorteningResponse shortenUrl(String originalUrl) {

        String normalizedUrl = normalize(originalUrl);

        // 검증
        if (!urlValidator.isValid(normalizedUrl)) {
            throw new InvalidUrlException("유효하지 않은 url입니다.");
        }

        // 기본 상태 설정
        ShortenedUrlStatus status = shortenedUrlStatusRepository
                .findById(DEFAULT_STATUS_ID)
                .orElseThrow(() -> new UrlStatusNotFoundException("URL을 단축시킬 수 없습니다. 잠시 후 다시 시도해 주세요."));

        // 키 값 생성
        String code = randomCodeGenerator.generateRandomCode();
        ShortenedUrl newEntity = new ShortenedUrl(status, normalizedUrl, code, DEFAULT_URL_EXPIRATION_PERIOD);

        try {
            ShortenedUrl saved = shortenedUrlRepository.save(newEntity);
            String shortenedUrl = domain + "/" + saved.getCode();

            return new UrlShorteningResponse(shortenedUrl, saved.getExpiredAt());
        } catch (DataIntegrityViolationException exception) {
            throw new UrlShorteningFailureException("URL을 단축시킬 수 없습니다. 잠시 후 다시 시도해 주세요.");
        }
    }

    public String getOriginalUrl(String code) {
        return shortenedUrlRepository
                .findByCode(code)
                .orElseThrow(() -> new UrlNotFoundException("url을 찾을 수 없습니다."))
                .getOriginalUrl();
    }

    private String normalize(String url) {
        String str = url.trim();

        if (REGEX_HTTP_PREFIX.matcher(str).matches()) {
            return str;
        } else {
            return "https://" + str;
        }
    }
}
