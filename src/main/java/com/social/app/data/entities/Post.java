package com.social.app.data.entities;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a 140 character message that is posted by the user on the
 * social application.
 */
public final class Post implements Comparable<Post> {

    private static final int MSG_LEN = 140;

    private final Long id;

    private final LocalDateTime date;

    private final User author;

    @Size(max = MSG_LEN)
    private String message;

    public Post(final Long id,
                final LocalDateTime dateTime,
                final User author,
                final String msg) {
        assert id != null;
        assert dateTime != null;
        assert author != null;
        assert msg != null;

        if (msg.length() > MSG_LEN || msg.isEmpty()) {
            throw new IllegalArgumentException(String.format("Message must be "
                    + "between 1 and %d characters long.", MSG_LEN));
        }

        this.id = id;
        this.date = dateTime;
        this.author = author;
        this.message = msg;
    }

    /**
     * Returns the post id.
     *
     * @return post id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Returns the date-time the post was created.
     *
     * @return date-time of post
     */
    public LocalDateTime getDateTime() {
        return this.date;
    }

    /**
     * Returns the author of the post.
     *
     * @return author of post
     */
    public User getAuthor() {
        return this.author;
    }

    /**
     * Returns message content - 140 characters.
     *
     * @return message content.
     */
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Post post = (Post) o;
        return Objects.equals(this.id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public int compareTo(final Post other) {
        return other.getDateTime().compareTo(this.getDateTime());
    }
}
