package io.changhyun.surl.data.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "shortened_urls")
@Getter
public class ShortenedUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private ShortenedUrlStatus status;

    @Column(columnDefinition = "text", nullable = false)
    private String originalUrl;

    @Setter
    @Column(nullable = false, unique = true, length = 100)
    private String code;

    @Column(nullable = false)
    private Instant expiredAt;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant modifiedAt;

    protected ShortenedUrl() {

    }

    public ShortenedUrl(ShortenedUrlStatus status, String originalUrl, String code, int days) {
        this.status = status;
        this.originalUrl = originalUrl;
        this.code = code;

        Instant now = Instant.now();
        this.createdAt = now;
        this.modifiedAt = now;
        this.expiredAt = now.plus(days, ChronoUnit.DAYS);
    }

}
