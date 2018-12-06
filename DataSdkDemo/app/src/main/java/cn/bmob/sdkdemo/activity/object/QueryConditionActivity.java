package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


    @BindView(R.id.btn_count)
    AppCompatButton mBtnCount;
    @BindView(R.id.btn_compound_and)
    AppCompatButton mBtnCompoundAnd;
    @BindView(R.id.btn_compound_or)
    AppCompatButton mBtnCompoundOr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.btn_count, R.id.btn_compound_and, R.id.btn_compound_or})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_count:
                count();
                break;
            case R.id.btn_compound_and:
                compoundAnd();
                break;
            case R.id.btn_compound_or:
                compoundOr();
                break;
            default:
                break;
        }
    }


    /**
     * 复合查询：与查询，查询序列大于等于5并且小于等于10的类别
     */
    private void compoundAnd() {
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
                    Snackbar.make(mBtnCount, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnCount, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 复合查询：或查询，查询序列小于等于5或者大于等于10的类别
     */
    private void compoundOr() {
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
                    Snackbar.make(mBtnCount, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnCount, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 查询结果计数
     */
    private void count() {
        BmobQuery<Category> bmobQuery = new BmobQuery();
        bmobQuery.count(Category.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnCount, "查询成功：" + count, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnCount, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
