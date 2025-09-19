package io.changhyun.surl.component;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomCodeGenerator {
    private static final String ALPHABETS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int RANDOM_CODE_LENGTH = 8;

    public String generateRandomCode() {
        StringBuilder sb = new StringBuilder(RANDOM_CODE_LENGTH);

        for (int i = 0; i < RANDOM_CODE_LENGTH; i++) {
            int idx = secureRandom.nextInt(ALPHABETS.length());
            sb.append(ALPHABETS.charAt(idx));
        }

        return sb.toString();
    }
}
