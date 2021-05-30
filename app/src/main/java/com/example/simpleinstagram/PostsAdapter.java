package com.example.simpleinstagram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public static final String TAG = "PostsAdapter";

    private Context context;
    private List<Post> posts;
    private List<String> likedPostIds;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        likedPostIds = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> newPosts) {
        posts.addAll(newPosts);
        notifyDataSetChanged();
    }

    public void addLikedPostId(String id) {
        likedPostIds.add(id);
    }

    public void removeLikedPostId(String id) {
        likedPostIds.remove(id);
    }

    public void clearLikedPostIds() {
        likedPostIds.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvDate;
        private CheckBox cbLike;
        private TextView tvLikeNum;

        private boolean changedByProgram;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            cbLike = itemView.findViewById(R.id.cbLike);
            tvLikeNum = itemView.findViewById(R.id.tvLikeNum);
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
            tvDate.setText(post.getDate());
            tvLikeNum.setText("" + post.getLikes());

            // Set like icon to unchecked by default
            changedByProgram = true;
            cbLike.setChecked(false);
            // If user has liked the post, set the like icon to checked.
            for(String id : likedPostIds) {
                if(id.equals(post.getObjectId())) {
                    cbLike.setChecked(true);
                }
            }
            changedByProgram = false;

            cbLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Check if checkbox was changed by the user or by the program
                    if(!changedByProgram) {
                        Log.i(TAG, "Changed by user");

                        ParseUser user = ParseUser.getCurrentUser();

                        // If the checkbox is checked, then the post has been liked
                        if(isChecked) {
                            addLikedPostId(post.getObjectId());
                            user.add("likedPosts", post.getObjectId());
                            user.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e != null) {
                                        Log.e(TAG, "Error saving like to database", e);
                                    }
                                }
                            });

                            post.incrementLikes();
                            post.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e != null) {
                                        Log.e(TAG, "Error incrementing likes", e);
                                    }
                                }
                            });
                            tvLikeNum.setText("" + (Integer.parseInt((String)tvLikeNum.getText()) + 1));
                        }
                        else {
                            removeLikedPostId(post.getObjectId());
                            user.removeAll("likedPosts", Arrays.asList(post.getObjectId()));
                            user.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e != null) {
                                        Log.e(TAG, "Error saving unlike to database", e);
                                    }
                                }
                            });

                            post.decrementLikes();
                            post.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e != null) {
                                        Log.e(TAG, "Error decrementing likes", e);
                                    }
                                }
                            });
                            tvLikeNum.setText("" + (Integer.parseInt((String)tvLikeNum.getText()) - 1));
                        }
                    }
                    else {
                        Log.i(TAG, "Changed by program");
                    }
                }
            });
        }
    }
}
