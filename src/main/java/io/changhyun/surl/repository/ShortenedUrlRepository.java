package io.changhyun.surl.repository;

import io.changhyun.surl.data.domain.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, Long> {
}
