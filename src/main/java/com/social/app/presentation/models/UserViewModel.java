package com.social.app.presentation.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user with username.
 */
public final class UserViewModel {

    @JsonProperty(value = "username", required = true)
    private String username;

    UserViewModel() {
    }

    UserViewModel(final String username) {
        this.username = username;
    }

    /**
     * Returns username of user.
     * @return username of user
     */
    public String getUsername() {
        return this.username;
    }
}
