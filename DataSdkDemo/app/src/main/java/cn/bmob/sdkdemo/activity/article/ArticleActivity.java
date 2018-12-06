package cn.bmob.sdkdemo.activity.article;

import android.os.Bundle;

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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
        queryArticle();
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
                } else {
                    Snackbar.make(mBtnQueryArticle, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
