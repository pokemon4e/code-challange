/**
 *
 */
package com.social.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple social networking application, similar to Twitter.
 */
@SpringBootApplication
public class SocialApplication {

    /**
     * Starts the social application.
     *
     * @param args Unused
     */
    public static void main(final String[] args) {
        SpringApplication.run(SocialApplication.class, args);
    }
}
