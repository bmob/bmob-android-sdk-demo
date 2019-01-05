package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created on 2018/11/26 16:14
 *
 * @author zhangchaozhou
 */
public class QueryCacheActivity extends AppCompatActivity {
    @BindView(R.id.btn_cache_query)
    AppCompatButton mBtnCacheQuery;
    @BindView(R.id.btn_cache_clear)
    AppCompatButton mBtnCacheClear;
    @BindView(R.id.btn_cache_clear_all)
    AppCompatButton mBtnCacheClearAll;
    @BindView(R.id.btn_cache_set_max_age)
    AppCompatButton mBtnCacheSetMaxAge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_cache_query, R.id.btn_cache_clear, R.id.btn_cache_clear_all, R.id.btn_cache_set_max_age})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cache_query:
                queryCache();
                break;
            case R.id.btn_cache_clear:
                break;
            case R.id.btn_cache_clear_all:
                break;
            case R.id.btn_cache_set_max_age:
                break;

            default:
                break;
        }
    }


    /**
     * 缓存查询
     */
    private void queryCache() {
        BmobQuery<User> query = new BmobQuery<>();
        boolean isCache = query.hasCachedResult(User.class);
        if (isCache) {
            // 先从缓存取数据，如果没有的话，再从网络取。
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            // 如果没有缓存的话，则先从网络中取
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnCacheQuery, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnCacheQuery, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


}
