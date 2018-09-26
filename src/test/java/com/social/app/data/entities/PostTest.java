package com.social.app.data.entities;

import com.social.app.data.entities.Post;
import com.social.app.data.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

public class PostTest {

    private static final Long ID = 1L;
    private static final LocalDateTime DATE_TIME =
            LocalDateTime.of(2018, 1, 1, 3, 5);
    private static final User AUTHOR = new User(1L, "michael");
    private static final String MSG = "Post message";

    private Post sut;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void initialize() {
        this.sut = new Post(ID, DATE_TIME, AUTHOR, MSG);
    }

    @Test
    public void givenNullIdWhenConstructorInvokedThenTrhowAssertionError() {
        this.exception.expect(AssertionError.class);
        new Post(null, DATE_TIME, AUTHOR, MSG);
    }

    @Test
    public void givenNullDateTimeWhenConstructorInvokedThenTrhowAssertionError() {
        this.exception.expect(AssertionError.class);
        new Post(ID, null, AUTHOR, MSG);
    }

    @Test
    public void givenNullAutorWhenConstructorInvokedThenTrhowAssertionError() {
        this.exception.expect(AssertionError.class);
        new Post(ID, DATE_TIME, null, MSG);
    }

    @Test
    public void givenNullMessageWhenConstructorInvokedThenTrhowAssertionError() {
        this.exception.expect(AssertionError.class);
        new Post(ID, DATE_TIME, AUTHOR, null);
    }

    @Test
    public void givenEmptyMessageWhenConstructorInvokedThenTrhowIllegalArgumentException() {
        this.exception.expect(IllegalArgumentException.class);
        new Post(ID, DATE_TIME, AUTHOR, "");
    }

    @Test
    public void givenLongerMessageWhenConstructorInvokedThenTrhowIllegalArgumentException() {
        final String longMsg = new String(new char[141]).replace("\0", "a");
        this.exception.expect(IllegalArgumentException.class);
        new Post(ID, DATE_TIME, AUTHOR, longMsg);
    }


    @Test
    public void givenValidIdWhenGetIdInvokedThenReturnId() {
        final Long result = this.sut.getId();

        Assert.assertEquals(result, ID);
    }

    @Test
    public void givenValidDateTimeWhenGetDateTimeInvokedThenReturnDateTime() {
        final LocalDateTime result = this.sut.getDateTime();

        Assert.assertEquals(result, DATE_TIME);
    }

    @Test
    public void givenValidAuthorWhenGetAuthorInvokedThenReturnAuthor() {
        final User result = this.sut.getAuthor();

        Assert.assertEquals(result, AUTHOR);
    }

    @Test
    public void givenValidMessageWhenGetMessageInvokedThenReturnMessage() {
        final String result = this.sut.getMessage();

        Assert.assertEquals(result, MSG);
    }
}
