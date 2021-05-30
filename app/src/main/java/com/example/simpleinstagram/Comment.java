package com.example.simpleinstagram;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {

    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_USER = "user";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_POST_ID = "postId";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getContent() {
        return getString(KEY_CONTENT);
    }

    public void setContent(String content) {
        put(KEY_CONTENT, content);
    }

    public void setPostId(String postId) {
        put(KEY_POST_ID, postId);
    }
}
