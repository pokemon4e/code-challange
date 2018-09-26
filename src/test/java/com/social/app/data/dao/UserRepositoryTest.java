package com.social.app.data.dao;

import com.social.app.data.dao.IRepository;
import com.social.app.data.dao.impl.UserRepository;
import com.social.app.data.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Optional;

public class UserRepositoryTest {

    private static final Long ID = 1L;
    private static final String USERNAME = "micahel";

    private IRepository<User, Long> sut;
    private User user1;
    private User user2;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void initialize() {
        this.sut = new UserRepository();
        this.user1 = new User(ID + 1, USERNAME + "1");
        this.user2 = new User(ID + 2, USERNAME + "2");
    }

    @Test
    public void givenIdNullWhenFindByUsernameInvokedThenThrowIllegalArgumentException() {
        this.exception.expect(IllegalArgumentException.class);
        this.sut.findByUsername(null);
    }

    @Test
    public void givenNoUserSavedWhenFindByUsernameInvokedThenReturnOptionalEmpty() {
        Optional<User> result = this.sut.findByUsername("michael");

        Assert.assertEquals(Optional.empty(), result);
    }

    @Test
    public void givenUserSavedWhenFindByUsernameInvokedThenReturnOptionalWithUser() {
        this.sut.save(this.user1);
        this.sut.save(this.user2);
        final Optional<User> expected = Optional.of(this.user1);

        final Optional<User> result = this.sut.findByUsername(this.user1.getUsername());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void givenUserNulllWhenSaveInvokedThenThrowIllegalArgumentException() {
        this.exception.expect(IllegalArgumentException.class);
        this.sut.save(null);
    }

    @Test
    public void givenUsersSavedWhenFindAllInvokedThenReturnAllUsers() {
        this.sut.save(this.user1);
        this.sut.save(this.user2);

        final Collection<User> result = this.sut.findAll();

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(this.user1));
        Assert.assertTrue(result.contains(this.user2));
    }

    @Test
    public void givenUsersSavedWhenFindAllInvokedThenReturnAllUsersInUnmodifiableCollection() {
        this.sut.save(this.user1);

        final Collection<User> result = this.sut.findAll();

        this.exception.expect(UnsupportedOperationException.class);
        result.add(this.user2);
    }

    @Test
    public void givenUsersSavedTwiceWhenFindAllInvokedThenReturnUserOnce() {
        this.sut.save(this.user1);
        this.sut.save(new User(ID + 1, USERNAME + "1"));

        final Collection<User> result = this.sut.findAll();

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains(this.user1));
    }

    @Test
    public void givenUserWhenSavedThenReturnSameUser() {
        final User result = this.sut.save(this.user1);

        Assert.assertEquals(this.user1, result);
    }

}
