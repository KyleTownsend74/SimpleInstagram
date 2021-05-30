package com.example.simpleinstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simpleinstagram.Adapters.CommentsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    public static final String TAG = "CommentActivity";

    private EditText etComment;
    private Button btnPostComment;
    private RecyclerView rvComments;
    private CommentsAdapter commentsAdapter;
    private List<Comment> allComments;
    private String currentPostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        etComment = findViewById(R.id.etComment);
        btnPostComment = findViewById(R.id.btnPostComment);
        rvComments = findViewById(R.id.rvComments);

        allComments = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(this, allComments);

        rvComments.setAdapter(commentsAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));

        currentPostId = getIntent().getStringExtra("postId");

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etComment.getText().toString();

                if(content.isEmpty()) {
                    Toast.makeText(CommentActivity.this, "Comment must have content.", Toast.LENGTH_SHORT).show();
                }
                else {
                    ParseUser user = ParseUser.getCurrentUser();
                    saveComment(content, user, currentPostId);
                }
            }
        });

        queryComments();
    }

    private void queryComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_USER);
        query.whereEqualTo(Comment.KEY_POST_ID, currentPostId);
        query.setLimit(20);
        query.addDescendingOrder(Comment.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Error fetching comments", e);
                    return;
                }

                commentsAdapter.clear();
                commentsAdapter.addAll(comments);
            }
        });
        // Add column to comment class in database that stores the associated post id.
        // Then, when querying for the comments in a post, use query.whereEqualTo(postIdKey, currentPostId);
    }

    private void saveComment(String content, ParseUser user, String postId) {
        Comment comment = new Comment();

        comment.setContent(content);
        comment.setUser(user);
        comment.setPostId(postId);
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Error saving comment", e);
                    Toast.makeText(CommentActivity.this, "Error while saving comment!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Saved comment");
                etComment.setText("");
            }
        });
    }
}