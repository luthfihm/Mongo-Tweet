package com.pat.tugas9;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.UuidRepresentation;

import javax.print.Doc;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by luthfi on 24/11/15.
 */
public class MongoTweet {
    private String hostname;
    private String keyspace;
    private MongoCollection<Document> userCollections;
    private MongoCollection<Document> followersCollections;
    private MongoCollection<Document> friendsCollections;
    private MongoCollection<Document> timelineCollections;
    private MongoCollection<Document> userlineCollections;
    private MongoCollection<Document> tweetsCollections;

    public MongoTweet(String hostname, String keyspace) {
        this.hostname = hostname;
        this.keyspace = keyspace;
        MongoClient mongoClient = new MongoClient( hostname , 27017 );
        MongoDatabase database = mongoClient.getDatabase(keyspace);
        userCollections = database.getCollection("user");
        followersCollections = database.getCollection("followers");
        friendsCollections = database.getCollection("friends");
        timelineCollections = database.getCollection("timeline");
        userlineCollections = database.getCollection("userline");
        tweetsCollections = database.getCollection("tweets");
    }

    public void registerUser(User user) {
        userCollections.insertOne(user.toBSON());
    }

    public User getUser(String username) {
        User user = null;
        Document userBSON = userCollections.find(eq("username",username)).first();
        if (userBSON != null) {
            user = new User(userBSON.getString("username"),userBSON.getString("password"));
        }
        return user;
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<User>();
        for (Document userBSON : userCollections.find()) {
            User user = new User(userBSON.getString("username"),userBSON.getString("password"));
            users.add(user);
        }
        return users;
    }

    public boolean isFollowing(User user, User follower) {
        if (followersCollections.find(and(eq("username", user.getUsername()), eq("follower", follower.getUsername()))).first() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public void followFriend(User follower, User friend) {
        Date since = new Date();
        Document followerBSON = new Document("username",friend.getUsername())
                .append("follower",follower.getUsername())
                .append("since",since);
        Document friendBSON = new Document("username",follower.getUsername())
                .append("friend",friend.getUsername())
                .append("since",since);
        followersCollections.insertOne(followerBSON);
        friendsCollections.insertOne(friendBSON);
    }

    public List<User> getFollowers(String username) {
        List<User> followers = new ArrayList<User>();
        for (Document followerBSON : followersCollections.find(eq("username",username))) {
            User follower = getUser(followerBSON.getString("follower"));
            followers.add(follower);
        }
        return followers;
    }

    public void postTweet(Tweet tweet) {
        Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
        tweetsCollections.insertOne(tweet.toBSON());
        Document userlineBSON = new Document("username",tweet.getUsername())
                .append("time",time.toString())
                .append("tweet_id",tweet.getTweet_id().toString());
        Document timelineBSON = new Document("username",tweet.getUsername())
                .append("time",time.toString())
                .append("tweet_id",tweet.getTweet_id().toString());
        userlineCollections.insertOne(userlineBSON);
        timelineCollections.insertOne(timelineBSON);
        List<User> followers = this.getFollowers(tweet.getUsername());
        for (User follower : followers) {
            timelineBSON = new Document("username",follower.getUsername())
                    .append("time",time.toString())
                    .append("tweet_id",tweet.getTweet_id().toString());
            timelineCollections.insertOne(timelineBSON);
        }
    }

    public Tweet getTweet(UUID tweet_id) {
        Tweet tweet = null;
        Document tweetBSON = tweetsCollections.find(eq("tweet_id",tweet_id.toString())).first();
        if (tweetBSON != null) {
            tweet = new Tweet();
            tweet.setTweet_id(UUID.fromString(tweetBSON.getString("tweet_id")));
            tweet.setUsername(tweetBSON.getString("username"));
            tweet.setBody(tweetBSON.getString("body"));
        }
        return tweet;
    }

    public List<Tweet> getTweets(String username) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (Document tweetBSON : tweetsCollections.find(eq("username",username))) {
            Tweet tweet = new Tweet();
            tweet.setTweet_id(UUID.fromString(tweetBSON.getString("tweet_id")));
            tweet.setUsername(tweetBSON.getString("username"));
            tweet.setBody(tweetBSON.getString("body"));
            tweets.add(tweet);
        }
        return tweets;
    }

    public List<Timeline> getUserTimeline(String username) {
        List<Timeline> timelineList = new ArrayList<Timeline>();

        for (Document timelineBSON : timelineCollections.find(eq("username",username))) {
            Timeline timeline = new Timeline();
            timeline.setUsername(username);
            timeline.setTime(timelineBSON.getString("time"));
            timeline.setTweet(this.getTweet(UUID.fromString(timelineBSON.getString("tweet_id"))));
            timelineList.add(timeline);
        }
        return timelineList;
    }
}
