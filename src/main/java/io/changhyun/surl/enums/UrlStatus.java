package io.changhyun.surl.enums;

import lombok.Getter;

@Getter
public enum UrlStatus {
    ACTIVE("ACTIVE"),
    EXPIRED("EXPIRED"),
    DELETED("DELETED")
    ;

    private final String label;

    UrlStatus(String label) {
        this.label = label;
    }
}
