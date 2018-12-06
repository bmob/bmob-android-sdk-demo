package cn.bmob.sdkdemo.activity.relevance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


    @BindView(R.id.rv_post)
    RecyclerView mRvPost;
    @BindView(R.id.fab_add_post)
    FloatingActionButton mFabAddPost;
    @BindView(R.id.fab_update_post)
    FloatingActionButton mFabUpdatePost;
    @BindView(R.id.fab_remove_post)
    FloatingActionButton mFabRemovePost;


    private PostAdapter mPostAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer);
        ButterKnife.bind(this);


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


    @OnClick({R.id.fab_query_post, R.id.fab_add_post, R.id.fab_update_post, R.id.fab_remove_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_query_post:
                queryPostAuthor();
                break;
            case R.id.fab_add_post:
                savePost();
                break;
            case R.id.fab_update_post:
                updatePostAuthor();
                break;
            case R.id.fab_remove_post:
                removePostAuthor();
                break;

            default:

                break;
        }
    }


    /**
     * 添加一对一关联，当前用户发布帖子
     */
    private void savePost() {
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
                        Snackbar.make(mFabAddPost, "发布帖子成功：" + s, Snackbar.LENGTH_LONG).show();
                    } else {
                        Log.e("BMOB", e.toString());
                        Snackbar.make(mFabAddPost, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Snackbar.make(mFabAddPost, "请先登录", Snackbar.LENGTH_LONG).show();
        }
    }


    /**
     * 修改一对一关联，修改帖子和用户的关系
     */
    private void updatePostAuthor() {
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
                    Snackbar.make(mFabAddPost, "修改帖子成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mFabAddPost, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 删除一对一关联，解除帖子和用户的关系
     */
    private void removePostAuthor() {
        Post post = new Post();
        //需要修改，否则会有internal error
        post.setObjectId("此处填写需要修改的帖子");
        //删除一对一关联，解除帖子和用户的关系
        post.remove("author");
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mFabAddPost, "修改帖子成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mFabAddPost, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 查询一对一关联，查询当前用户发表的所有帖子
     */
    private void queryPostAuthor() {
        if (BmobUser.isLogin()) {
            BmobQuery<Post> query = new BmobQuery<>();
            query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("author");
            query.findObjects(new FindListener<Post>() {

                @Override
                public void done(List<Post> object, BmobException e) {
                    if (e == null) {
                        Snackbar.make(mFabAddPost, "查询成功", Snackbar.LENGTH_LONG).show();
                    } else {
                        Log.e("BMOB", e.toString());
                        Snackbar.make(mFabAddPost, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }

            });
        } else {
            Snackbar.make(mFabAddPost, "请先登录", Snackbar.LENGTH_LONG).show();
        }
    }


}
