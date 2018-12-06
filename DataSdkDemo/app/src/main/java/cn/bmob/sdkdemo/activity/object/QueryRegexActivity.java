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
 * Created on 2018/11/23 17:43
 * TODO 模糊搜索/正则表达式搜索 此功能需要付费才可以正常使用
 *
 * @author zhangchaozhou
 */
public class QueryRegexActivity extends AppCompatActivity {
    @BindView(R.id.btn_regex)
    AppCompatButton mBtnRegex;
    @BindView(R.id.btn_contains)
    AppCompatButton mBtnContains;
    @BindView(R.id.btn_starts)
    AppCompatButton mBtnStarts;
    @BindView(R.id.btn_ends)
    AppCompatButton mBtnEnds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_regex);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_regex, R.id.btn_contains, R.id.btn_starts, R.id.btn_ends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_regex:
                regex();
                break;
            case R.id.btn_contains:
                contains();
                break;
            case R.id.btn_starts:
                starts();
                break;
            case R.id.btn_ends:
                ends();
                break;

            default:
                break;
        }
    }

    private void ends() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEndsWith("name", "eg");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnRegex, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnRegex, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void starts() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereStartsWith("name", "eg");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnRegex, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnRegex, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void contains() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereContains("name", "eg");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnRegex, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnRegex, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void regex() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereMatches("name", "^[A-Z]\\d");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnRegex, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnRegex, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
