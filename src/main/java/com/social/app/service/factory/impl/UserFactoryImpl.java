package com.social.app.service.factory.impl;

import com.social.app.data.entities.User;
import com.social.app.service.factory.UserFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Provides methods for creating user entities.
 * Autogenerates the ids starting from 1.
 */
@Component
public final class UserFactoryImpl implements UserFactory {

    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public User createUser(final String username) {
        assert username != null;

        return new User(idSequence.incrementAndGet(), username);
    }
}
