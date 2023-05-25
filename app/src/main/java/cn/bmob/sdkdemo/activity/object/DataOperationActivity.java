package cn.bmob.sdkdemo.activity.object;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.Category;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

/**
 * Created on 2018/12/5 14:51
 *
 * @author zhangchaozhou
 */
public class DataOperationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_operation);
    }

    public void toQueryConditionActivity(View view) {
        startActivity(new Intent(this, QueryConditionActivity.class));
    }

    public void toSingleCrudActivity(View view) {
        startActivity(new Intent(this, SingleCrudActivity.class));
    }

    public void toMultiCrudActivity(View view) {
        startActivity(new Intent(this, MultiCrudActivity.class));
    }

    public void toQueryWhereActivity(View view) {
        startActivity(new Intent(this, QueryWhereActivity.class));
    }

    public void toQueryRegexActivity(View view) {
        startActivity(new Intent(this, QueryRegexActivity.class));
    }

    public void toQueryStatisticActivity(View view) {
        startActivity(new Intent(this, QueryStatisticActivity.class));
    }

    public void toQueryBqlActivity(View view) {
        startActivity(new Intent(this, QueryBqlActivity.class));
    }

    public void toQueryTableActivity(View view) {
        startActivity(new Intent(this, QueryTableActivity.class));
    }

    public void toQueryCacheActivity(View view) {
        startActivity(new Intent(this, QueryCacheActivity.class));
    }

    public void onSyncQuery(View view) {
        new Thread(() -> {
            BmobQuery<Category> bmobQuery = new BmobQuery<>();
            List<Category> objectsSync = null;
            try {
                objectsSync = bmobQuery.findObjectsSync(Category.class);
            } catch (BmobException bmobException) {
                bmobException.printStackTrace();
                Log.e("bmob", "查询失败:" + bmobException.getMessage());
            }
            if (objectsSync != null) {
                Snackbar.make(view, "查询成功：" + objectsSync.size(), Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(view, "查询失败", Snackbar.LENGTH_LONG).show();
            }
        }).start();
    }

}
