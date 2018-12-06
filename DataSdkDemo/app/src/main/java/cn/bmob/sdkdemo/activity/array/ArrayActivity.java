package cn.bmob.sdkdemo.activity.array;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
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
 * Created on 2018/11/22 17:57
 *
 * @author zhangchaozhou
 */
public class ArrayActivity extends AppCompatActivity {


    @BindView(R.id.btn_contain)
    AppCompatButton mBtnContain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_contain, R.id.btn_not_contain, R.id.btn_contain_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_contain:
                contain();
                break;
            case R.id.btn_not_contain:
                notContain();
                break;
            case R.id.btn_contain_all:
                containAll();
                break;
            default:
                break;
        }
    }


    /**
     * 当一个查询是另一个查询的条件时，称之为子查询。
     * 不包含
     */
    private void notContain() {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        String[] alias = new String[]{"A", "B"};
        userBmobQuery.addWhereNotContainedIn("alias", Arrays.asList(alias));
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnContain, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnContain, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 包含
     */
    private void contain() {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        String[] alias = new String[]{"A", "B"};
        userBmobQuery.addWhereContainedIn("alias", Arrays.asList(alias));
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnContain, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnContain, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 包含所有，查询别名为A和B的用户
     */
    private void containAll() {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        String[] alias = new String[]{"A", "B"};
        userBmobQuery.addWhereContainsAll("alias", Arrays.asList(alias));
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnContain, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnContain, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
