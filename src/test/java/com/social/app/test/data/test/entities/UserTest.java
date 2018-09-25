package com.social.app.data.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.util.Collection;

public class UserTest {

    private static final Long ID = 1L;
    private static final String USERNAME = "micahel";

    private User sut;
    private Post post1;
    private Post post2;
    private User user1;
    private User user2;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void initialize() {
        this.sut = new User(ID, USERNAME);
        LocalDateTime dateTime = LocalDateTime.of(2018, 10, 1, 10, 1);
        this.post1 = new Post(ID, dateTime, this.sut, "Message");
        this.post2 = new Post(ID + 1, dateTime, this.sut, "Message");
        this.user1 = new User(ID + 1, USERNAME + "1");
        this.user2 = new User(ID + 2, USERNAME + "2");
    }

    @Test
    public void givenNullIDWhenConstructorInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        new User(null, USERNAME);
    }

    @Test
    public void givenNullUsernameWhenConstructorInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        new User(ID, null);
    }

    @Test
    public void givenEmptyUsernameWhenConstructorInvokedThenThrowAssertionError() {
        this.exception.expect(IllegalArgumentException.class);
        new User(ID, "");
    }

    @Test
    public void givenValidIdWhenGetIDInvokedThenReturnId() {
        Long result = this.sut.getId();

        Assert.assertEquals(ID, result);
    }

    @Test
    public void givenValidUsernameWhenGetUsernameInvokedThenReturnUsername() {
        String result = this.sut.getUsername();

        Assert.assertEquals(USERNAME, result);
    }

    @Test
    public void givenNoPostsAddedWhenGetPostsInvokedThenReturnEmptyCollection() {
        Collection<Post> result = this.sut.getPosts();

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void givenNoUserFollowedWhenGetFollowingUsersInvokedThenReturnEmptyCollection() {
        Collection<User> result = this.sut.getFollowingUsers();

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void givenPostsWhenGetPostsInvokedThenReturnCollectionOfPosts() {
        this.sut.addPost(this.post1);

        Collection<Post> result = this.sut.getPosts();

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains(this.post1));
    }

    @Test
    public void givenPostsWhenGetPostsInvokedThenReturnUnmodifiableCollectionOfPosts() {
        this.sut.addPost(this.post1);

        Collection<Post> result = this.sut.getPosts();

        this.exception.expect(UnsupportedOperationException.class);
        result.add(this.post2);
    }

    @Test
    public void givenUserFollowedWhenGetFollowingUsersInvokedThenReturnCollectionOfUsers() {
        this.sut.followUser(this.user1);

        Collection<User> result = this.sut.getFollowingUsers();

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains(this.user1));
    }

    @Test
    public void givenUserFollowedWhenGetFollowingUsersInvokedThenReturnUnmodifiableCollectionOfUsers() {
        this.sut.followUser(this.user1);

        Collection<User> result = this.sut.getFollowingUsers();

        this.exception.expect(UnsupportedOperationException.class);
        result.add(this.user2);
    }
}

