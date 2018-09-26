package com.social.app.presentation.models;

import com.social.app.data.entities.Post;
import org.springframework.stereotype.Component;

/**
 * Maps entities to view-models.
 */
@Component
public final class Mapper {

    /**
     * Map post entitty to post view mode.
     * @param post post to be mapped
     * @return postViewModel
     */
    public PostViewModel mapPost(final Post post) {
        return new PostViewModel(
                post.getDateTime(),
                post.getAuthor().getUsername(),
                post.getMessage());
    }
}
