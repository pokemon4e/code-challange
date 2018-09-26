package com.social.app.service.factory;

import com.social.app.data.entities.Post;
import com.social.app.data.entities.User;
import com.social.app.service.factory.PostFactory;
import com.social.app.service.factory.impl.PostFactoryImpl;
import com.social.app.utils.DateTimeHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostFactoryTest {

    private static final String MSG = "Message!";

    private DateTimeHelper dateTimeHelperMock;
    private User user;
    private PostFactory sut;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void initialize() {
        this.dateTimeHelperMock = mock(DateTimeHelper.class);
        this.user = new User(1L, "Username");
        this.sut = new PostFactoryImpl(this.dateTimeHelperMock);
    }

    @Test
    public void givenNullDateTimeHelperWhenConstructorInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        new PostFactoryImpl(null);
    }


    @Test
    public void givenNullUserWhenCreatePostInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.createPost(null, MSG);
    }

    @Test
    public void givenNullMessageWhenCreatePostInvokedThenThrowAssertionError() {
        this.exception.expect(AssertionError.class);
        this.sut.createPost(this.user, null);
    }

    @Test
    public void givenValidUserAndMessageWhenCreatePostInvokedThenReturnPost() {
        LocalDateTime dateTime = LocalDateTime.of(2018, 9, 1, 10, 05);
        when(this.dateTimeHelperMock.nowUTC()).thenReturn(dateTime);

        Post result = this.sut.createPost(this.user, MSG);

        Assert.assertEquals(1, (long) result.getId());
        Assert.assertEquals(dateTime, result.getDateTime());
        Assert.assertEquals(this.user, result.getAuthor());
        Assert.assertEquals(MSG, result.getMessage());
    }

    @Test
    public void givenMultiplePostsWhenCreatePostInvokedThenAutoIncrementID() {
        LocalDateTime dateTime = LocalDateTime.of(2018, 9, 1, 10, 05);
        when(this.dateTimeHelperMock.nowUTC()).thenReturn(dateTime);

        Post result1 = this.sut.createPost(this.user, MSG);
        Post result2 = this.sut.createPost(this.user, MSG + "1");

        Assert.assertEquals(1, (long) result1.getId());
        Assert.assertEquals(2, (long) result2.getId());
    }

}
