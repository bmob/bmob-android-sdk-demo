package cn.bmob.sdkdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Comment;

/**
 * Created on 2018/11/28 16:19
 *
 * @author zhangchaozhou
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {


    private List<Comment> mComments;


    public CommentAdapter(List<Comment> comments) {
        mComments = comments;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, null);
        CommentHolder commentHolder = new CommentHolder(view);
        return commentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mComments == null ? 0 : mComments.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {


        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
