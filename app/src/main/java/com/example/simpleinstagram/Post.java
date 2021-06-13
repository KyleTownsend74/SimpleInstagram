package com.example.simpleinstagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_COMMENTS = "comments";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getDate() { return getCreatedAt().toString().substring(0, 16); }

    public int getLikes() {
        return getInt(KEY_LIKES);
    }

    public void incrementLikes() {
        put(KEY_LIKES, getLikes() + 1);
    }

    public void decrementLikes() {
        put(KEY_LIKES, getLikes() - 1);
    }

    public int getComments() {
        return getInt(KEY_COMMENTS);
    }

    public void incrementComments() {
        put(KEY_COMMENTS, getComments() + 1);
    }
}
