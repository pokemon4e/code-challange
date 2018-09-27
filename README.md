



# Code Challenge [![Build Status](https://travis-ci.com/pokemon4e/code-challange.svg?branch=master)](https://travis-ci.com/pokemon4e/code-challange)

Solution to the [code challange](https://github.com/HStoneAge/code-challenge/blob/master/README-DEVELOPERS.md) as part of the recruiting process for HSBC by  *Milena Sapunova*.

**Requires:** `java 8`, `maven`  

**Technologies used:** `java 8`, `maven`, `Spring Boot`, `mockito`, `checkstyle`

**Run application:** `mvn spring-boot:run`

**Base url:**  `http://localhost:8080`

## Description
A simple social networking application, similar to Twitter, exposed through a web API.

Supported scenarios:

### Posting
A user should be able to post a 140 character message.

To add a message to user's wall: `POST /user/{username}`

```json
{
   "message": "Your message here."
}
```

*Note:*
* If the message is empty or contains more than 140 characters a 400 Bad Request error is returned.
* If no user exists with given `{username}`, user is created and the post is added to their wall.

### Wall
A user should be able to see a list of the messages they've posted, in reverse chronological order.

To see all messages posted by user: `GET /user/{username}`

The output:

```json
[
    {
        "postedOn": "26-09-2018 07:09:54",
        "author": "alice",
        "message": "My second message here"
    },
    {
        "postedOn": "26-09-2018 07:09:50",
        "author": "alice",
        "message": "My first message here"
    }
]
```

### Following
A user should be able to follow another user. Following does not have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.

To follow another user: `POST /user/{username}/follow`

```json
{
    "username" : "bob"
}
```

*Note:*
* If user tries to follow himself a 400 Bad Request error is returned.

### Timeline
A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.

To see user's timeline: `GET /user/{username}/timeline`

```json
[
    {
        "postedOn": "26-09-2018 03:06:12",
        "author": "bob",
        "message": "Bob's second message here."
    },
    {
        "postedOn": "26-09-2018 03:05:40",
        "author": "bob",
        "message": "Bob's first message here."
    }
]
```

## Example
1. Alice posts few messages: `POST /user/alice`
    ```json
    {
       "message": "Alice's message here."
    }
    ```
    ```json
    {
       "message": "Alice's second message here."
    }
    ```
2. Alice wants to see her wall: `GET /user/alice`
    ```json
    [
        {
            "postedOn": "26-09-2018 10:42:38",
            "author": "alice",
            "message": "Alice's second message here."
        },
        {
            "postedOn": "26-09-2018 10:42:31",
            "author": "alice",
            "message": "Alice's message here."
        }
    ]
    ```
3. Bob posts a message: `POST /user/bob`
    ```json
    {
       "message": "Bob has a message."
    }
    ```
4. Megan posts a message: `POST /user/megan`
    ```json
    {
       "message": "Megan's post."
    }
    ```
5. Megan follows Alice and Bob: `POST /user/megan/follow`
    ```json
    {
        "username" : "alice"
    }
    ```
    ```json
    {
        "username" : "bob"
    }
    ```
    
6. Megan wants to see her timeline: `GET /user/megan/timeline`
    ```json
    [
        {
            "postedOn": "26-09-2018 10:43:33",
            "author": "bob",
            "message": "Bob has a message."
        },
        {
            "postedOn": "26-09-2018 10:42:38",
            "author": "alice",
            "message": "Alice's second message here."
        },
        {
            "postedOn": "26-09-2018 10:42:31",
            "author": "alice",
            "message": "Alice's message here."
        }
    ]
    ```