package com.social.app.service.impl;

import com.social.app.data.dao.IRepository;
import com.social.app.data.entities.Post;
import com.social.app.data.entities.User;
import com.social.app.service.UserService;
import com.social.app.service.factory.PostFactory;
import com.social.app.service.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService.
 */
@Service
public final class UserServiceImpl implements UserService {

    private final IRepository<User, Long> userRepository;
    private final UserFactory userFactory;
    private final PostFactory postFactory;

    @Autowired
    public UserServiceImpl(
            final IRepository<User, Long> userRepository,
            final UserFactory userFactory,
            final PostFactory postFactory) {
        assert userRepository != null;
        assert userFactory != null;
        assert postFactory != null;

        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.postFactory = postFactory;
    }

    @Override
    public Collection<Post> getUserPosts(final String username) {
        assert username != null;

        return this.getUserByUsername(username).getPosts();
    }

    @Override
    public Collection<Post> getUserTimeline(final String username) {
        assert username != null;

        final User user = this.getUserByUsername(username);

        return user.getFollowingUsers().stream()
                .flatMap(u -> u.getPosts().stream())
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(final String username) {
        assert username != null;

        if (this.userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User already exists.");
        }

        return this.userRepository.save(this.userFactory.createUser(username));
    }

    @Override
    public Post addPost(final String username, final String message) {
        assert username != null;
        assert message != null;

        Optional<User> opt = this.userRepository.findByUsername(username);
        final User user = opt.orElseGet(() -> createUser(username));
        final Post post = this.postFactory.createPost(user, message);

        return user.addPost(post);
    }

    @Override
    public void followUser(final String username, final String followUsername) {
        assert username != null;
        assert followUsername != null;

        final User user = this.getUserByUsername(username);
        final User followedUser = this.getUserByUsername(followUsername);

        user.followUser(followedUser);
    }

    private User getUserByUsername(final String username) {
        assert username != null;

        Optional<User> opt = this.userRepository.findByUsername(username);

        if (!opt.isPresent()) {
            throw new IllegalArgumentException("No such user");
        }

        return opt.get();
    }

}
