package com.social.app.service.factory.impl;

import com.social.app.data.entities.Post;
import com.social.app.data.entities.User;
import com.social.app.service.factory.PostFactory;
import com.social.app.utils.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Provides methods for creating post entities. Uses {@code DateTimeHelper}
 * for retrieving the current date-time. Autogenerates the ids starting from 1.
 */
@Component
public final class PostFactoryImpl implements PostFactory {

    private final AtomicLong idSequence = new AtomicLong(0);

    private DateTimeHelper dateTime;

    @Autowired
    public PostFactoryImpl(final DateTimeHelper dateTime) {
        assert dateTime != null;

        this.dateTime = dateTime;
    }

    @Override
    public Post createPost(final User user, final String message) {
        assert user != null;
        assert message != null;

        return new Post(
                idSequence.incrementAndGet(),
                this.dateTime.nowUTC(),
                user,
                message);
    }
}
