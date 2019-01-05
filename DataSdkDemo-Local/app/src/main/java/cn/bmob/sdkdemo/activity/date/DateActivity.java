package cn.bmob.sdkdemo.activity.date;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created on 2018/11/22 17:55
 *
 * @author zhangchaozhou
 */
public class DateActivity extends AppCompatActivity {
    @BindView(R.id.btn_equal)
    AppCompatButton mBtnEqual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_equal, R.id.btn_not_equal, R.id.btn_less, R.id.btn_large, R.id.btn_equal_less, R.id.btn_equal_large, R.id.btn_equal_large_equal_less})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_equal:
                try {
                    equal();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_not_equal:
                try {
                    notEqual();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_less:
                try {
                    less();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_large:
                try {
                    large();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_equal_less:
                try {
                    lessEqual();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_equal_large:
                try {
                    largeEqual();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_equal_large_equal_less:
                try {
                    duration();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 期间
     */
    private void duration() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String createdAtStart = "2018-11-23 10:29:59";
        Date createdAtDateStart = sdf.parse(createdAtStart);
        BmobDate bmobCreatedAtDateStart = new BmobDate(createdAtDateStart);

        String createdAtEnd = "2018-11-23 10:30:01";
        Date createdAtDateEnd = sdf.parse(createdAtEnd);
        BmobDate bmobCreatedAtDateEnd = new BmobDate(createdAtDateEnd);


        BmobQuery<Category> categoryBmobQueryStart = new BmobQuery<>();
        categoryBmobQueryStart.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart);
        BmobQuery<Category> categoryBmobQueryEnd = new BmobQuery<>();
        categoryBmobQueryEnd.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd);
        List<BmobQuery<Category>> queries = new ArrayList<>();
        queries.add(categoryBmobQueryStart);
        queries.add(categoryBmobQueryStart);


        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.and(queries);
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
     * 某个时间
     */
    private void equal() throws ParseException {
        String createdAt = "2018-11-23 10:30:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = sdf.parse(createdAt);
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);


        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("createdAt", bmobCreatedAtDate);
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
     * 某个时间外
     */
    private void notEqual() throws ParseException {
        String createdAt = "2018-11-23 10:30:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = sdf.parse(createdAt);
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);


        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereNotEqualTo("createdAt", bmobCreatedAtDate);
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
     * 某个时间前
     */
    private void less() throws ParseException {
        String createdAt = "2018-11-23 10:30:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = sdf.parse(createdAt);
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);


        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereLessThan("createdAt", bmobCreatedAtDate);
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
     * 某个时间及以前
     */
    private void lessEqual() throws ParseException {
        String createdAt = "2018-11-23 10:30:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = sdf.parse(createdAt);
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);


        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
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
     * 某个时间后
     */
    private void large() throws ParseException {
        String createdAt = "2018-11-23 10:30:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = sdf.parse(createdAt);
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);


        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereGreaterThan("createdAt", bmobCreatedAtDate);
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
     * 某个时间及以后
     */
    private void largeEqual() throws ParseException {
        String createdAt = "2018-11-23 10:30:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = sdf.parse(createdAt);
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);


        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDate);
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
