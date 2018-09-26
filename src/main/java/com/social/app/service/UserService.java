package com.social.app.service;

import com.social.app.data.entities.Post;
import com.social.app.data.entities.User;

import java.util.Collection;

/**
 * User Service that holds the business logic for the social app.
 */
public interface UserService {

    /**
     * Returns all the messages a user with the given username has posted, in
     * reverse chronological order.
     *
     * @param username username of user
     * @return all posts
     * @throws IllegalArgumentException if no user with given username found
     */
    Collection<Post> getUserPosts(String username);

    /**
     * Returns all the messages posted by users followed by a user with given
     * username. Messages are in reverse chronological order.
     *
     * @param username username of user
     * @return all posts of followed users
     * @throws IllegalArgumentException if no user with given username found
     */
    Collection<Post> getUserTimeline(String username);

    /**
     * Creates user with given username.
     *
     * @param username username of user
     * @return the created user
     * @throws IllegalArgumentException if user with given username already
     *                                  exists
     */
    User createUser(String username);

    /**
     * Post message from user with given username. If no user with given
     * username is found, a user is created.
     *
     * @param username username of user
     * @param message  message to be posted
     * @return post
     */
    Post addPost(String username, String message);

    /**
     * Follow another user.
     *
     * @param username       username of user
     * @param followUsername username of user to be followed
     * @throws IllegalArgumentException if users with given usernames are not
     *                                  found
     */
    void followUser(String username, String followUsername);
}
