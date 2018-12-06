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
import cn.bmob.sdkdemo.bean.Category;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created on 2018/11/22 17:20
 *
 * @author zhangchaozhou
 */
public class QueryWhereActivity extends AppCompatActivity {


    @BindView(R.id.btn_equal)
    AppCompatButton mBtnEqual;
    @BindView(R.id.btn_not_equal)
    AppCompatButton mBtnNotEqual;
    @BindView(R.id.btn_less)
    AppCompatButton mBtnLess;
    @BindView(R.id.btn_large)
    AppCompatButton mBtnLarge;
    @BindView(R.id.btn_equal_less)
    AppCompatButton mBtnEqualLess;
    @BindView(R.id.btn_equal_large)
    AppCompatButton mBtnEqualLarge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_equal, R.id.btn_not_equal, R.id.btn_less, R.id.btn_large, R.id.btn_equal_less, R.id.btn_equal_large})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_equal:
                equal();
                break;
            case R.id.btn_not_equal:
                notEqual();
                break;
            case R.id.btn_less:
                less();
                break;
            case R.id.btn_large:
                large();
                break;
            case R.id.btn_equal_less:
                lessEqual();
                break;
            case R.id.btn_equal_large:
                largeEqual();
                break;
            default:
                break;
        }
    }

    /**
     * name为football的类别
     */
    private void equal() {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("name", "football");
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnEqual, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * name不为football的类别
     */
    private void notEqual() {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereNotEqualTo("name", "football");
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnEqual, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * sequence小于10的类别
     */
    private void less() {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereLessThan("sequence", 10);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnEqual, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * sequence小于等于10的类别
     */
    private void lessEqual() {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereLessThanOrEqualTo("sequence", 10);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnEqual, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * sequence大于10的类别
     */
    private void large() {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereGreaterThan("sequence", 10);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnEqual, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * sequence大于等于10的类别
     */
    private void largeEqual() {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereGreaterThanOrEqualTo("sequence", 10);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnEqual, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
