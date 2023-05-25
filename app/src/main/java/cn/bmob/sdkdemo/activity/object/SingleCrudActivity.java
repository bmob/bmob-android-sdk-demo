package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Category;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 单条增删改查
 *
 * @author zhangchaozhou
 */
public class SingleCrudActivity extends AppCompatActivity {

    private String mObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_single);
    }

    /**
     * 新增一个对象
     */
    public void onSave(View view) {
        Category category = new Category();
        category.setName("football");
        category.setDesc("足球\n篮球");
        category.setSequence(1);
        category.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    mObjectId = objectId;
                    Snackbar.make(view, "新增成功：" + mObjectId, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 更新一个对象
     */
    public void onUpdate(View view) {
        Category category = new Category();
        category.setSequence(2);
        category.update(mObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "更新成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 删除一个对象
     */
    public void onDelete(View view) {
        Category category = new Category();
        category.delete(mObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "删除成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 查询一个对象
     */
    public void onQuery(View view) {
        BmobQuery<Category> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mObjectId, new QueryListener<Category>() {
            @Override
            public void done(Category category, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + category.getName(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
