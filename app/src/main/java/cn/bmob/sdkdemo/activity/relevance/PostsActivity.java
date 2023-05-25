package cn.bmob.sdkdemo.activity.relevance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.adapter.PostAdapter;
import cn.bmob.sdkdemo.bean.Post;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 2018/11/26 09:33
 *
 * @author zhangchaozhou
 */
public class PostsActivity extends AppCompatActivity {

    RecyclerView mRvPost;
    private PostAdapter mPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer);

        mRvPost = findViewById(R.id.rv_post);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvPost.setLayoutManager(linearLayoutManager);
        mRvPost.setHasFixedSize(true);

        queryPosts();

    }

    private void queryPosts() {

        BmobQuery<Post> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> posts, BmobException e) {
                mPostAdapter = new PostAdapter(posts);
                mRvPost.setAdapter(mPostAdapter);
            }
        });
    }

    /**
     * 添加一对一关联，当前用户发布帖子
     */
    public void onSavePost(View view) {
        if (BmobUser.isLogin()) {
            Post post = new Post();
            post.setTitle("帖子标题");
            post.setContent("帖子内容");
            //添加一对一关联，用户关联帖子
            post.setAuthor(BmobUser.getCurrentUser(User.class));
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Snackbar.make(view, "发布帖子成功：" + s, Snackbar.LENGTH_LONG).show();
                    } else {
                        Log.e("BMOB", e.toString());
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Snackbar.make(view, "请先登录", Snackbar.LENGTH_LONG).show();
        }
    }


    /**
     * 修改一对一关联，修改帖子和用户的关系
     */
    public void onUpdatePostAuthor(View view) {
        User user = new User();
        //需要修改，否则会有internal error
        user.setObjectId("此处填写你需要关联的用户");
        Post post = new Post();
        //需要修改，否则会有internal error
        post.setObjectId("此处填写需要修改的帖子");
        //修改一对一关联，修改帖子和用户的关系
        post.setAuthor(user);
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "修改帖子成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 删除一对一关联，解除帖子和用户的关系
     */
    public void onRemovePostAuthor(View view) {
        Post post = new Post();
        //需要修改，否则会有internal error
        post.setObjectId("此处填写需要修改的帖子");
        //删除一对一关联，解除帖子和用户的关系
        post.remove("author");
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "修改帖子成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 查询一对一关联，查询当前用户发表的所有帖子
     */
    public void onQueryPostAuthor(View view) {
        if (BmobUser.isLogin()) {
            BmobQuery<Post> query = new BmobQuery<>();
            query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("author");
            query.include("test");
            query.findObjects(new FindListener<Post>() {

                @Override
                public void done(List<Post> object, BmobException e) {
                    if (e == null) {
                        Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show();
                    } else {
                        Log.e("BMOB", e.toString());
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }

            });
        } else {
            Snackbar.make(view, "请先登录", Snackbar.LENGTH_LONG).show();
        }
    }


}
