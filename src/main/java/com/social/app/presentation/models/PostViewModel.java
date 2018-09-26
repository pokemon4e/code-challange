package com.social.app.presentation.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Represents a post with date-time, author and message.
 */
public final class PostViewModel {

    @JsonProperty("postedOn")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dateTime;

    @JsonProperty("author")
    private String author;

    @JsonProperty(value = "message", required = true)
    private String message;

    PostViewModel() {
    }

    PostViewModel(
            final LocalDateTime dateTime,
            final String author,
            final String message) {
        this.dateTime = dateTime;
        this.author = author;
        this.message = message;
    }

    /**
     * Returns the date-time in UTC when the post was created.
     *
     * @return date-time
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Rerturns username of author.
     *
     * @return username of author
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Rerturns message of post.
     *
     * @return message of post
     */
    public String getMessage() {
        return this.message;
    }
}
