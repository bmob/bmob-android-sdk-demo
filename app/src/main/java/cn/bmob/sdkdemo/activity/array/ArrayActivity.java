package cn.bmob.sdkdemo.activity.array;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created on 2018/11/22 17:57
 *
 * @author zhangchaozhou
 */
public class ArrayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array);
    }

    /**
     * 包含
     *
     * @param view
     */
    public void onContain(View view) {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        String[] alias = new String[]{"A", "B"};
        userBmobQuery.addWhereContainedIn("alias", Arrays.asList(alias));
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 当一个查询是另一个查询的条件时，称之为子查询。
     * 不包含
     *
     * @param view
     */
    public void onNotContain(View view) {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        String[] alias = new String[]{"A", "B"};
        userBmobQuery.addWhereNotContainedIn("alias", Arrays.asList(alias));
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 包含所有，查询别名为A和B的用户
     *
     * @param view
     */
    public void onContainAll(View view) {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        String[] alias = new String[]{"A", "B"};
        userBmobQuery.addWhereContainsAll("alias", Arrays.asList(alias));
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
