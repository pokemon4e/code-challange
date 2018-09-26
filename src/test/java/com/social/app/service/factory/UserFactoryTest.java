package com.social.app.service.factory;

import com.social.app.data.entities.User;
import com.social.app.service.factory.impl.UserFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserFactoryTest {

    private static final String USERNAME = "username!";

    private UserFactory sut;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void initialize() {
        this.sut = new UserFactoryImpl();
    }

    @Test
    public void givenNullUsernameWhenCreateUserInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.createUser(null);
    }

    @Test
    public void givenValidUserAndMessageWhenCreatePostInvokedThenReturnPost() {
        User result = this.sut.createUser(USERNAME);

        Assert.assertEquals(1, (long) result.getId());
        Assert.assertEquals(USERNAME, result.getUsername());
    }

    @Test
    public void givenMultiplePostsWhenCreatePostInvokedThenAutoIncrementID() {
        User result1 = this.sut.createUser(USERNAME);
        User result2 = this.sut.createUser(USERNAME + "1");

        Assert.assertEquals(1, (long) result1.getId());
        Assert.assertEquals(2, (long) result2.getId());
    }

}
