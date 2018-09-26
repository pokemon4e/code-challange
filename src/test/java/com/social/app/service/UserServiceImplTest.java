package com.social.app.service;

import com.social.app.data.dao.IRepository;
import com.social.app.data.entities.Post;
import com.social.app.data.entities.User;
import com.social.app.service.factory.PostFactory;
import com.social.app.service.factory.UserFactory;
import com.social.app.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private static final Long ID = 1L;
    private static final String USERNAME = "michael";
    private static final LocalDateTime DT = LocalDateTime.of(2018, 1, 1, 3, 5);
    private static final String MSG = "Message!";

    private IRepository<User, Long> repoMock;
    private UserFactory userFactoryMock;
    private PostFactory postFactoryMock;
    private User user1;
    private User user2;
    private Post post1;
    private Post post2;
    private UserService sut;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void initialize() {
        this.repoMock = mock(IRepository.class);
        this.userFactoryMock = mock(UserFactory.class);
        this.postFactoryMock = mock(PostFactory.class);
        this.user1 = new User(ID, USERNAME);
        this.user2 = new User(ID + 1, USERNAME + "1");
        this.post1 = new Post(ID, DT, this.user1, MSG);
        this.post2 = new Post(ID + 1, DT.plusHours(2), this.user1, MSG + "1");
        this.user1.addPost(this.post1);
        this.user1.addPost(this.post2);
        this.sut = new UserServiceImpl(
                this.repoMock, this.userFactoryMock, this.postFactoryMock);
    }

    @Test
    public void givenNullUserRepositoryWhenConstructorInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        new UserServiceImpl(null, this.userFactoryMock, this.postFactoryMock);
    }

    @Test
    public void givenNullUserFactoryWhenConstructorInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        new UserServiceImpl(this.repoMock, null, this.postFactoryMock);
    }

    @Test
    public void givenNullPostFactoryWhenConstructorInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        new UserServiceImpl(this.repoMock, this.userFactoryMock, null);
    }

    @Test
    public void givenNullUsernameWhenGetUsersPostsInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.getUserPosts(null);
    }

    @Test
    public void givenNoUserWithThisUsernameWhenGetUserPostsInvokedThenThrowIllegalArgumentException() {
        when(this.repoMock.findByUsername(USERNAME)).thenReturn(Optional.empty());

        this.exception.expect(IllegalArgumentException.class);
        this.sut.getUserPosts(USERNAME);
    }

    @Test
    public void givenUserWithThisUsernameWhenGetUserPostsInvokedThenReturnPosts() {
        when(this.repoMock.findByUsername(USERNAME))
                .thenReturn(Optional.of(this.user1));

        Collection<Post> result = this.sut.getUserPosts(USERNAME);

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(this.post1));
        Assert.assertTrue(result.contains(this.post2));
    }

    @Test
    public void givenUserWithThisUsernameWhenGetUserPostsInvokedThenReturnPostsInReverseChronologicalOrder() {
        when(this.repoMock.findByUsername(USERNAME))
                .thenReturn(Optional.of(this.user1));

        List<Post> result = new ArrayList<>(this.sut.getUserPosts(USERNAME));

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(this.post2, result.get(0));
        Assert.assertEquals(this.post1, result.get(1));
    }

    @Test
    public void givenNullUsernameWhenGetTimelineInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.getUserTimeline(null);
    }

    @Test
    public void givenNoUserWithThisUsernameWhenGetTimelineInvokedThenThrowillegalArgumentException() {
        when(this.repoMock.findByUsername(USERNAME))
                .thenReturn(Optional.empty());

        this.exception.expect(IllegalArgumentException.class);
        this.sut.getUserTimeline(USERNAME);
    }

    @Test
    public void givenUserWithThisUsernameWhenGetUserTimelineInvokedThenReturnPosts() {
        this.user1.followUser(this.user2);
        this.user2.addPost(this.post1);
        this.user2.addPost(this.post2);
        when(this.repoMock.findByUsername(USERNAME))
                .thenReturn(Optional.of(this.user1));

        Collection<Post> result = this.sut.getUserPosts(USERNAME);

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(this.post1));
        Assert.assertTrue(result.contains(this.post2));
    }

    @Test
    public void givenUserWithThisUsernameWhenGetUserTimelineInvokedThenReturnPostsInReverseChronologicalOrder() {
        this.user1.followUser(this.user2);
        this.user2.addPost(this.post1);
        this.user2.addPost(this.post2);
        when(this.repoMock.findByUsername(USERNAME))
                .thenReturn(Optional.of(this.user1));

        List<Post> result = new ArrayList<>(this.sut.getUserPosts(USERNAME));

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(this.post2, result.get(0));
        Assert.assertEquals(this.post1, result.get(1));
    }

    @Test
    public void givenNullUsernameWhenCreateUserInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.createUser(null);
    }

    @Test
    public void givenUserWithUsernameAlreadyExistsWhenCreateUserInvokedThenThrowIllegalArgumentException() {
        when(this.repoMock.findByUsername(USERNAME))
                .thenReturn(Optional.of(this.user1));

        this.exception.expect(IllegalArgumentException.class);
        this.sut.createUser(this.user1.getUsername());
    }

    @Test
    public void givenValidUsernameWhenCreateUserInvokedThenSaveUser() {
        when(this.userFactoryMock.createUser(USERNAME)).thenReturn(this.user1);
        this.sut.createUser(USERNAME);

        verify(this.repoMock).findByUsername(USERNAME);
        verify(this.userFactoryMock).createUser(USERNAME);
        verify(this.repoMock).save(this.user1);
    }

    @Test
    public void givenNullUsernameWhenAddPostInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.addPost(null, this.post1.getMessage());
    }

    @Test
    public void givenNullPostWhenAddPostInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.addPost(USERNAME, null);
    }

    @Test
    public void givenNoUserWithUsernameWhenAddPostInvokedThenCreateUser() {
        User user = new User(3L, USERNAME);
        Post post = new Post(30L, DT, user, MSG);
        when(this.userFactoryMock.createUser(USERNAME)).thenReturn(user);
        when(this.postFactoryMock.createPost(user, MSG)).thenReturn(post);
        when(this.repoMock.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(this.repoMock.save(user)).thenReturn(user);

        this.sut.addPost(USERNAME, MSG);

        verify(this.userFactoryMock).createUser(USERNAME);
        verify(this.postFactoryMock).createPost(user, MSG);
        verify(this.repoMock).save(user);
        Assert.assertEquals(1L, user.getPosts().size());
        Assert.assertTrue(user.getPosts().contains(post));
    }

    @Test
    public void givenUserWithUsernameWhenAddPostInvokedThenAddPost() {
        User user = new User(3L, USERNAME);
        Post post = new Post(30L, DT, user, MSG);
        when(this.postFactoryMock.createPost(user, MSG)).thenReturn(post);
        when(this.repoMock.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(this.repoMock.save(user)).thenReturn(user);

        this.sut.addPost(USERNAME, MSG);

        verify(this.postFactoryMock).createPost(user, MSG);
        verify(this.repoMock, never()).save(user);
        Assert.assertEquals(1L, user.getPosts().size());
        Assert.assertTrue(user.getPosts().contains(post));
    }

    @Test
    public void givenNullUsernameWhenFollowUserInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.followUser(null, USERNAME);
    }

    @Test
    public void givenNullFollowUsernameWhenFollowUserInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.followUser(USERNAME, null);
    }

    @Test
    public void givenNoUserWithUsernameWhenFollowUserInvokedThenThrowIllegalArgumentException() {
        when(this.repoMock.findByUsername(USERNAME)).thenReturn(Optional.empty());

        this.exception.expect(IllegalArgumentException.class);
        this.sut.followUser(USERNAME, USERNAME + "1");
    }

    @Test
    public void givenNoUserWithFollowUsernameWhenFollowUserInvokedThenThrowIllegalArgumentException() {
        when(this.repoMock.findByUsername(USERNAME)).thenReturn(Optional.of(this.user1));
        when(this.repoMock.findByUsername(USERNAME + "1")).thenReturn(Optional.empty());

        this.exception.expect(IllegalArgumentException.class);
        this.sut.followUser(USERNAME, USERNAME + "1");
    }

    @Test
    public void givenUserFollowingHimslefWhenFollowUserInvokedThenThrowIllegalArgumentException() {
        this.exception.expect(IllegalArgumentException.class);
        this.sut.followUser(USERNAME, USERNAME);
    }

    @Test
    public void givenValidUsernamesWhenFollowUserInvokedThenFollowUser() {
        when(this.repoMock.findByUsername(this.user1.getUsername()))
                .thenReturn(Optional.of(this.user1));
        when(this.repoMock.findByUsername(this.user2.getUsername()))
                .thenReturn(Optional.of(this.user2));

        this.sut.followUser(this.user1.getUsername(), this.user2.getUsername());

        Assert.assertEquals(1, this.user1.getFollowingUsers().size());
        Assert.assertTrue(this.user1.getFollowingUsers().contains(this.user2));
    }

}
