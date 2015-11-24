package com.pat.tugas9;


import org.bson.Document;

import java.util.UUID;

/**
 * Created by luthfi on 11/12/2015.
 */
public class Tweet {
    private UUID tweet_id;
    private String username;
    private String body;

    public Tweet() {
        this.tweet_id = UUID.randomUUID();
        this.username = null;
        this.body = null;
    }

    public Tweet(String username, String body) {
        this.tweet_id = UUID.randomUUID();
        this.username = username;
        this.body = body;
    }

    public UUID getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(UUID tweet_id) {
        this.tweet_id = tweet_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Document toBSON() {
        return new Document("tweet_id",tweet_id.toString())
                .append("username",username)
                .append("body",body);
    }
}
