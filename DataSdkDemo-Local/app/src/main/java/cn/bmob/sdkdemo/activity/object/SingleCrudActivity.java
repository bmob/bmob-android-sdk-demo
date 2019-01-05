package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.btn_save)
    AppCompatButton mBtnSave;
    @BindView(R.id.btn_query)
    AppCompatButton mBtnQuery;
    @BindView(R.id.btn_update)
    AppCompatButton mBtnUpdate;
    @BindView(R.id.btn_delete)
    AppCompatButton mBtnDelete;
    private String mObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_single);
        ButterKnife.bind(this);
    }


    /**
     * 新增一个对象
     */
    private void save() {
        Category category = new Category();
        category.setName("football");
        category.setDesc("足球");
        category.setSequence(1);
        category.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    mObjectId = objectId;
                    Snackbar.make(mBtnSave, "新增成功：" + mObjectId, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnSave, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 更新一个对象
     */
    private void update() {
        Category category = new Category();
        category.setSequence(2);
        category.update(mObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnUpdate, "更新成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnUpdate, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 删除一个对象
     */
    private void delete() {
        Category category = new Category();
        category.delete(mObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnDelete, "删除成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnDelete, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 查询一个对象
     */
    private void query() {
        BmobQuery<Category> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mObjectId, new QueryListener<Category>() {
            @Override
            public void done(Category category, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnQuery, "查询成功：" + category.getName(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnQuery, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick({R.id.btn_save, R.id.btn_update, R.id.btn_delete, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_query:
                query();
                break;
            default:
                break;
        }
    }
}
