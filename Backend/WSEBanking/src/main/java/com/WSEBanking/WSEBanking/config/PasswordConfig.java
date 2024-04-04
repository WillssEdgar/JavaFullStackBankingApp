package com.WSEBanking.WSEBanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Configuration class for password encoding.
 */
@Component
public class PasswordConfig {

    /**
     * Provides a bean for password encoding.
     *
     * @return A PasswordEncoder bean configured with BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
