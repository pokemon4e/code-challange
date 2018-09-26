package com.social.app.data.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Represents a user of the social application.
 */
public final class User {

    private final Long id;

    private final String username;

    private final Set<Post> posts;

    private final Set<User> followingUsers;

    public User(final Long id, final String username) {
        assert id != null;
        assert username != null;

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }

        this.id = id;
        this.username = username;
        this.posts = new ConcurrentSkipListSet<>();
        this.followingUsers = ConcurrentHashMap.newKeySet();
    }

    /**
     * Returns user id.
     *
     * @return user id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Returns username.
     *
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns all posts of the user as unmodifiable collection in reverse
     * chronological order.
     *
     * @return user posts
     */
    public Collection<Post> getPosts() {
        return Collections.unmodifiableCollection(this.posts);
    }

    /**
     * Returns all users followed by the user as unmodifiable collection.
     *
     * @return users followed by the user
     */
    public Collection<User> getFollowingUsers() {
        return Collections.unmodifiableCollection(this.followingUsers);
    }

    /**
     * Adds post.
     *
     * @param post post to be added
     * @return post
     */
    public Post addPost(final Post post) {
        this.posts.add(post);
        return post;
    }

    /**
     * Follow another user.
     *
     * @param user user to be followed
     */
    public void followUser(final User user) {
        this.followingUsers.add(user);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return Objects.equals(this.id, user.id)
                && Objects.equals(this.username, user.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.username);
    }
}
