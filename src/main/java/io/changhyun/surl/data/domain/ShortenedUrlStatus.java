package io.changhyun.surl.data.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ShortenedUrlStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 15)
    private String statusName;

}
