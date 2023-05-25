package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_regex);
    }

    public void onEnds(View view) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEndsWith("name", "eg");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onStarts(View view) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereStartsWith("name", "eg");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onContains(View view) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereContains("username", "9");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onRegex(View view) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereMatches("name", "^[A-Z]\\d");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
