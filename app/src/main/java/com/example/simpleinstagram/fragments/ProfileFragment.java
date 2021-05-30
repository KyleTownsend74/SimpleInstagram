package com.example.simpleinstagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simpleinstagram.LoginActivity;
import com.example.simpleinstagram.Post;
import com.example.simpleinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends PostsFragment{

    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();

                // Go back to login, finish main activity with signed out user
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
    }

    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // Prepare to add liked posts to adapter
                adapter.clearLikedPostIds();
                ParseUser user = ParseUser.getCurrentUser();
                JSONArray likedPosts = user.getJSONArray("likedPosts");

                // Add liked posts
                if(likedPosts != null) {
                    for(int i = 0; i < likedPosts.length(); i++) {
                        try {
                            adapter.addLikedPostId(likedPosts.get(i).toString());
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                else {
                    user.put("likedPosts", new ArrayList<>());
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null) {
                                Log.e(TAG, "Error initializing user likes", e);
                            }
                        }
                    });
                }

                for(Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                adapter.clear();
                adapter.addAll(posts);
            }
        });
    }
}
