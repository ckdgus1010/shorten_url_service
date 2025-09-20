package io.changhyun.surl.service;

import io.changhyun.surl.data.domain.ShortenedUrl;
import io.changhyun.surl.enums.UrlStatus;
import io.changhyun.surl.exception.UrlNotFoundException;
import io.changhyun.surl.repository.ShortenedUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlReadService {

    private final ShortenedUrlRepository shortenedUrlRepository;

    public String findOriginalUrl(String code) {
        Optional<ShortenedUrl> optional = shortenedUrlRepository.findByCode(code);

        if (optional.isEmpty()) {
            log.warn("저장된 Original URL을 찾을 수 없습니다. code = {}", code);
            throw new UrlNotFoundException("URL을 찾을 수 없습니다.");
        }

        ShortenedUrl entity = optional.get();
        String status = entity.getStatus().getStatusName();

        if (status.equals(UrlStatus.ACTIVE.getLabel())) {
            return entity.getOriginalUrl();
        } else {
            // TODO: 실패 화면으로 redirect
            return null;
        }
    }
}
