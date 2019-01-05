package cn.bmob.sdkdemo.activity.relevance;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Post;

/**
 * Created on 2018/11/28 17:01
 *
 * @author zhangchaozhou
 */
public class CommentActivity extends AppCompatActivity {


    private Post mPost;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        mPost = (Post) getIntent().getSerializableExtra("post");


    }






}
