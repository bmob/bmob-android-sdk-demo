package cn.bmob.sdkdemo.activity.article;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobArticle;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created on 2018/11/27 10:25
 *
 * @author zhangchaozhou
 */
public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
    }


    /**
     * 查询图文消息
     */
    public void onQueryArticle(View view) {
        BmobQuery<BmobArticle> bmobArticleBmobQuery = new BmobQuery<>();
        bmobArticleBmobQuery.findObjects(new FindListener<BmobArticle>() {
            @Override
            public void done(List<BmobArticle> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();

                    for (BmobArticle article:object){
                        System.out.println(article.getTitle());
                        System.out.println(article.getUrl());
                    }
                } else {
                    Snackbar.make(view, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
