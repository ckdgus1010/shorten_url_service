package io.changhyun.surl.repository;

import io.changhyun.surl.data.domain.ShortenedUrlStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenedUrlStatusRepository extends JpaRepository<ShortenedUrlStatus, Long> {
}
