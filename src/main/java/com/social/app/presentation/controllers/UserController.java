package com.social.app.presentation.controllers;

import com.social.app.presentation.models.Mapper;
import com.social.app.presentation.models.PostViewModel;
import com.social.app.presentation.models.UserViewModel;
import com.social.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User Controler.
 */
@RestController
@RequestMapping("user")
public final class UserController {

    private UserService userService;
    private Mapper mapper;

    public UserController(final UserService userService, final Mapper mapper) {
        assert userService != null;

        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * Returns all posts of user with given username in reverse chronological
     * order.
     *
     * @param username username of user
     * @return all posts.
     */
    @GetMapping(path = "{username}")
    public List<PostViewModel> getPosts(final @PathVariable String username) {
        return this.userService.getUserPosts(username)
                .stream()
                .map(p -> this.mapper.mapPost(p))
                .collect(Collectors.toList());
    }

    /**
     * Add post by user with given username.
     *
     * @param username username of user
     * @param post     post
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{username}")
    public void postPost(final @PathVariable String username,
                         final @Valid @RequestBody PostViewModel post) {

        this.userService.addPost(username, post.getMessage());
    }

    /**
     * Returns all posts of users followed by user with given username in
     * reverse chronological order.
     *
     * @param username username of user
     * @return all posts of followed users.
     */
    @GetMapping(path = "{username}/timeline")
    public List<PostViewModel> getTimeline(
            final @PathVariable String username) {
        return this.userService.getUserTimeline(username)
                .stream()
                .map(p -> this.mapper.mapPost(p))
                .collect(Collectors.toList());
    }

    /**
     * Follow another user.
     *
     * @param username username of user
     * @param user     user to be followed
     */
    @PostMapping(path = "{username}/follow")
    public void postPost(final @PathVariable String username,
                         final @NotNull @RequestBody UserViewModel user) {
        this.userService.followUser(username, user.getUsername());
    }

}
