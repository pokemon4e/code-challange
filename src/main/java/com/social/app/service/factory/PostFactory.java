package com.social.app.service.factory;

import com.social.app.data.entities.Post;
import com.social.app.data.entities.User;
import org.springframework.stereotype.Component;

/**
 * Provides methods for creating post entities.
 */
public interface PostFactory {

    /**
     * Creates a post with autogenerated id, current date-time in UTC, author
     * and message.
     * @param author author of the post
     * @param message message of the post
     * @return post
     */
    Post createPost(User author, String message);

}
