package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Category;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

/**
 * 查询数据
 *
 * @author zhangchaozhou
 */
public class QueryConditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
    }

    /**
     * 复合查询：与查询，查询序列大于等于5并且小于等于10的类别
     */
    public void onCompoundAnd(View view) {
        BmobQuery<Category> categoryBmobQueryStart = new BmobQuery<>();
        categoryBmobQueryStart.addWhereGreaterThanOrEqualTo("sequence", 5);
        BmobQuery<Category> categoryBmobQueryEnd = new BmobQuery<>();
        categoryBmobQueryEnd.addWhereLessThanOrEqualTo("sequence", 10);
        List<BmobQuery<Category>> queries = new ArrayList<>();
        queries.add(categoryBmobQueryStart);
        queries.add(categoryBmobQueryStart);

        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.and(queries);
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
     * 复合查询：或查询，查询序列小于等于5或者大于等于10的类别
     */
    public void onCompoundOr(View view) {
        BmobQuery<Category> categoryBmobQueryStart = new BmobQuery<>();
        categoryBmobQueryStart.addWhereLessThanOrEqualTo("sequence", 5);
        BmobQuery<Category> categoryBmobQueryEnd = new BmobQuery<>();
        categoryBmobQueryEnd.addWhereGreaterThanOrEqualTo("sequence", 10);
        List<BmobQuery<Category>> queries = new ArrayList<>();
        queries.add(categoryBmobQueryStart);
        queries.add(categoryBmobQueryStart);

        BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.or(queries);
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
     * 查询结果计数
     */
    public void onCount(View view) {
        BmobQuery<Category> bmobQuery = new BmobQuery();
        bmobQuery.count(Category.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + count, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
