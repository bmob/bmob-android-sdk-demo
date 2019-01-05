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
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;

/**
 * 批量增删改查
 *
 * @author zhangchaozhou
 */
public class MultiCrudActivity extends AppCompatActivity {

    @BindView(R.id.btn_save)
    AppCompatButton mBtnSave;
    @BindView(R.id.btn_query)
    AppCompatButton mBtnQuery;
    @BindView(R.id.btn_update)
    AppCompatButton mBtnUpdate;
    @BindView(R.id.btn_delete)
    AppCompatButton mBtnDelete;
    @BindView(R.id.btn_save_update_delete)
    AppCompatButton mBtnSaveUpdateDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_multi);
        ButterKnife.bind(this);
    }


    /**
     * 新增多条数据
     */
    private void save() {
        List<BmobObject> categories = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Category category = new Category();
            category.setName("category" + i);
            category.setDesc("类别" + i);
            category.setSequence(i);
            categories.add(category);
        }
        new BmobBatch().insertBatch(categories).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Snackbar.make(mBtnSave, "第" + i + "个数据批量添加成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt(), Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mBtnSave, "第" + i + "个数据批量添加失败：" + ex.getMessage() + "," + ex.getErrorCode(), Snackbar.LENGTH_LONG).show();

                        }
                    }
                } else {
                    Snackbar.make(mBtnSave, "失败：" + e.getMessage() + "," + e.getErrorCode(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 更新多条数据
     */
    private void update() {

        List<BmobObject> categories = new ArrayList<>();

        Category category = new Category();
        category.setObjectId("此处填写对应的需要修改数据的objectId");
        category.setName("name" + System.currentTimeMillis());
        category.setDesc("类别" + System.currentTimeMillis());

        Category category1 = new Category();
        category1.setObjectId("此处填写对应的需要修改数据的objectId");
        category1.setName("name" + System.currentTimeMillis());
        category1.setDesc("类别" + System.currentTimeMillis());

        Category category2 = new Category();
        category2.setObjectId("此处填写对应的需要修改数据的objectId");
        category2.setName("name" + System.currentTimeMillis());
        category2.setDesc("类别" + System.currentTimeMillis());

        categories.add(category);
        categories.add(category1);
        categories.add(category2);

        new BmobBatch().updateBatch(categories).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Snackbar.make(mBtnUpdate, "第" + i + "个数据批量更新成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt(), Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mBtnUpdate, "第" + i + "个数据批量更新失败：" + ex.getMessage() + "," + ex.getErrorCode(), Snackbar.LENGTH_LONG).show();

                        }
                    }
                } else {
                    Snackbar.make(mBtnUpdate, "失败：" + e.getMessage() + "," + e.getErrorCode(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 删除多条数据
     */
    private void delete() {
        List<BmobObject> categories = new ArrayList<>();

        Category category = new Category();
        category.setObjectId("此处填写对应的需要删除数据的objectId");

        Category category1 = new Category();
        category1.setObjectId("此处填写对应的需要删除数据的objectId");

        Category category2 = new Category();
        category2.setObjectId("此处填写对应的需要删除数据的objectId");

        categories.add(category);
        categories.add(category1);
        categories.add(category2);

        new BmobBatch().deleteBatch(categories).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Snackbar.make(mBtnDelete, "第" + i + "个数据批量删除成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt(), Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mBtnDelete, "第" + i + "个数据批量删除失败：" + ex.getMessage() + "," + ex.getErrorCode(), Snackbar.LENGTH_LONG).show();

                        }
                    }
                } else {
                    Snackbar.make(mBtnDelete, "失败：" + e.getMessage() + "," + e.getErrorCode(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }





    public interface OnQueryListener<T>{
        void onSuccess(List<T> ts);
        void onError(BmobException ex);
    }



    public void query(final OnQueryListener onQueryListener){
        BmobQuery<Category> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> categories, BmobException e) {
                if (e == null) {
                    onQueryListener.onSuccess(categories);
                    Snackbar.make(mBtnQuery, "查询成功：" + categories.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    onQueryListener.onError(e);
                    Snackbar.make(mBtnQuery, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    /**
     * 查询多条数据
     */
    private void query() {
        BmobQuery<Category> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Category>() {
            @Override
            public void done(List<Category> categories, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnQuery, "查询成功：" + categories.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnQuery, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick({R.id.btn_save, R.id.btn_query, R.id.btn_update, R.id.btn_delete, R.id.btn_save_update_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_query:
//                query();





                query(new OnQueryListener() {
                    @Override
                    public void onSuccess(List list) {
                        System.out.println("success "+list.size());
                    }

                    @Override
                    public void onError(BmobException ex) {

                        System.err.println("error "+ex.getMessage());

                    }
                });
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_save_update_delete:
                saveUpdateDelete();
                break;
            default:
                break;
        }
    }


    /**
     * 同时新增、更新、删除多条数据
     */
    private void saveUpdateDelete() {
        BmobBatch batch = new BmobBatch();

        //批量添加
        List<BmobObject> categoriesSave = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Category category = new Category();
            category.setName("category" + i);
            category.setDesc("类别" + i);
            category.setSequence(i);
            categoriesSave.add(category);
        }


        //批量更新
        List<BmobObject> categoriesUpdate = new ArrayList<>();
        Category categoryUpdate = new Category();
        categoryUpdate.setObjectId("此处填写对应的需要修改数据的objectId");
        categoryUpdate.setName("name" + System.currentTimeMillis());
        categoryUpdate.setDesc("类别" + System.currentTimeMillis());
        Category categoryUpdate1 = new Category();
        categoryUpdate1.setObjectId("此处填写对应的需要修改数据的objectId");
        categoryUpdate1.setName("name" + System.currentTimeMillis());
        categoryUpdate1.setDesc("类别" + System.currentTimeMillis());
        Category categoryUpdate2 = new Category();
        categoryUpdate2.setObjectId("此处填写对应的需要修改数据的objectId");
        categoryUpdate2.setName("name" + System.currentTimeMillis());
        categoryUpdate2.setDesc("类别" + System.currentTimeMillis());
        categoriesUpdate.add(categoryUpdate);
        categoriesUpdate.add(categoryUpdate1);
        categoriesUpdate.add(categoryUpdate2);


        //批量删除
        List<BmobObject> categoriesDelete = new ArrayList<>();
        Category categoryDelete = new Category();
        categoryDelete.setObjectId("此处填写对应的需要删除数据的objectId");
        Category categoryDelete1 = new Category();
        categoryDelete1.setObjectId("此处填写对应的需要删除数据的objectId");
        Category categoryDelete2 = new Category();
        categoryDelete2.setObjectId("此处填写对应的需要删除数据的objectId");
        categoriesDelete.add(categoryDelete);
        categoriesDelete.add(categoryDelete1);
        categoriesDelete.add(categoryDelete2);


        //执行批量操作
        batch.insertBatch(categoriesSave);
        batch.updateBatch(categoriesUpdate);
        batch.deleteBatch(categoriesDelete);
        batch.doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    //返回结果的results和上面提交的顺序是一样的，请一一对应
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        //只有批量添加才返回objectId
                        if (ex == null) {
                            Snackbar.make(mBtnSaveUpdateDelete, "第" + i + "个数据批量操作成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt(), Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mBtnSaveUpdateDelete, "第" + i + "个数据批量操作失败：" + ex.getMessage() + "," + ex.getErrorCode(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Snackbar.make(mBtnSaveUpdateDelete, "失败：" + e.getMessage() + "," + e.getErrorCode(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}
