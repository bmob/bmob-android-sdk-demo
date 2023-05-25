package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Category;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created on 2018/11/22 17:20
 *
 * @author zhangchaozhou
 */
public class QueryWhereActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where);
    }

    /**
     * name为football的类别
     */
    public void onEqual(View view) {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
//        categoryBmobQuery.addWhereEqualTo("name", "football");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String createdAtStart = "2019-04-24 00:00:00";
        Date createdAtDateStart = null;
        try {
            createdAtDateStart = sdf.parse(createdAtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobDate date = new BmobDate(createdAtDateStart);


        categoryBmobQuery.addWhereEqualTo("date", date);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
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
     * name不为football的类别
     */
    public void onNotEqual(View view) {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereNotEqualTo("name", "football");
        categoryBmobQuery.addWhereNotEqualTo("bool", true);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
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
     * sequence小于10的类别
     */
    public void onLess(View view) {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereLessThan("sequence", 10);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
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
     * sequence小于等于10的类别
     */
    public void onLessEqual(View view) {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereLessThanOrEqualTo("sequence", 10);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
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
     * sequence大于10的类别
     */
    public void onLarge(View view) {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereGreaterThan("sequence", 10);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
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
     * sequence大于等于10的类别
     */
    public void onLargeEqual(View view) {
        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereGreaterThanOrEqualTo("sequence", 10);
        categoryBmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> object, BmobException e) {
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
