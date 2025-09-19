package io.changhyun.surl;

import org.apache.commons.validator.routines.UrlValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Apache Url Validator 테스트")
public class ApacheUrlValidatorTest {

    private static final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    @DisplayName("성공 케이스")
    @Test
    public void SuccessCases() {
        // given
        String[] arr = new String[]{
                "http://example.com",
                "http://www.example.com",
                "https://example.com",
                "https://www.example.com"
        };

        // when & then
        for (String url : arr) {
            boolean isValid = urlValidator.isValid(url);
            assertThat(isValid).isTrue();
        }
    }

    @DisplayName("실패 케이스")
    @Test
    public void FailureCases() {
        // given
        String[] arr = new String[]{
                "http://",
                "example.com",
                "ftp://example.com",
                "https://://example.com",
                "https:///example.com",
                "https:example.com",
        };

        // when & then
        for (String url : arr) {
            boolean isValid = urlValidator.isValid(url);
            assertThat(isValid).isFalse();
        }
    }
}
