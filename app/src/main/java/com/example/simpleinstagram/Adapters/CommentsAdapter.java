package com.example.simpleinstagram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleinstagram.Comment;
import com.example.simpleinstagram.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context context;
    private List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Comment> newComments) {
        comments.addAll(newComments);
        notifyDataSetChanged();
    }

    public void addToBeginning(Comment newComment) {
        comments.add(0, newComment);
        notifyItemInserted(0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsernameComment;
        private TextView tvComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsernameComment = itemView.findViewById(R.id.tvUsernameComment);
            tvComment = itemView.findViewById(R.id.tvComment);
        }

        public void bind(Comment comment) {
            tvUsernameComment.setText(comment.getUser().getUsername());
            tvComment.setText(comment.getContent());
        }
    }
}
