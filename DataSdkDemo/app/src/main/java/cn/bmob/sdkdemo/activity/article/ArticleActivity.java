package cn.bmob.sdkdemo.activity.article;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobArticle;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 2018/11/27 10:25
 *
 * @author zhangchaozhou
 */
public class ArticleActivity extends AppCompatActivity {

    @BindView(R.id.btn_query_article)
    AppCompatButton mBtnQueryArticle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_query_article)
    public void onViewClicked() {
//        queryArticle();
        updateArticle();
    }


    /**
     * 查询图文消息
     */
    private void queryArticle() {
        BmobQuery<BmobArticle> bmobArticleBmobQuery = new BmobQuery<>();
        bmobArticleBmobQuery.findObjects(new FindListener<BmobArticle>() {
            @Override
            public void done(List<BmobArticle> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnQueryArticle, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                    for (BmobArticle article :object){
                        System.out.println(article.getTitle());
                        System.out.println(article.getUrl());
                    }
                } else {
                    Snackbar.make(mBtnQueryArticle, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateArticle(){
        // 必须使用user.login方法登录成，才可以使用下面的BmobUser.getCurrentUser方法获取本地用户对象进行更新，
        // 否则不能更新用户信息
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
//        Log.i("bmob", "原始："+myUser.getMlike().getObjects());
        BmobArticle article = new BmobArticle();
        article.setObjectId("eCYF0007");
        BmobRelation relation = new BmobRelation();
        relation.add(article);
        myUser.setMlike(relation);
        myUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","更新用户信息成功");
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
    }

    class MyUser  extends BmobUser {
        private BmobRelation mlike;

        public BmobRelation getMlike() {
            return mlike;
        }

        public void setMlike(BmobRelation mlike) {
            this.mlike = mlike;
        }
    }
}
