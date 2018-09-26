package com.social.app.data.dao.impl;

import com.social.app.data.dao.IRepository;
import com.social.app.data.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository that manages the user type.
 */
@Repository
public final class UserRepository implements IRepository<User, Long> {

    private final Map<String, User> usernameUsers;

    public UserRepository() {
        this.usernameUsers = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username must not be null.");
        }

        Optional<User> result = Optional.empty();

        if (this.usernameUsers.containsKey(username)) {
            result = Optional.of(this.usernameUsers.get(username));
        }

        return result;
    }

    @Override
    public Collection<User> findAll() {
        return Collections.unmodifiableCollection(this.usernameUsers.values());
    }

    @Override
    public User save(final User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }

        this.usernameUsers.put(user.getUsername(), user);

        return user;
    }

}
