package cn.bmob.sdkdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Post;
import cn.bmob.sdkdemo.bean.User;

/**
 * Created on 2018/11/28 16:19
 *
 * @author zhangchaozhou
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {


    private List<Post> mPosts;

    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, null);
        PostHolder postHolder = new PostHolder(view);
        return postHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = mPosts.get(position);
        User author = post.getAuthor();
        if (author!=null){
            holder.mTvAuthorNickname.setText(author.getNickname());
        }
        holder.mTvPostTitle.setText(post.getTitle());
        holder.mTvPostContent.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return mPosts == null ? 0 : mPosts.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        AppCompatTextView mTvPostTitle;
        AppCompatTextView mTvPostContent;
        AppCompatTextView mTvAuthorNickname;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            mTvPostTitle = itemView.findViewById(R.id.tv_post_title);
            mTvPostContent = itemView.findViewById(R.id.tv_post_content);
            mTvAuthorNickname = itemView.findViewById(R.id.tv_author_nickname);
        }
    }
}
