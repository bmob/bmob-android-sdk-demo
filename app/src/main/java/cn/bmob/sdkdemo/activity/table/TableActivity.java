package cn.bmob.sdkdemo.activity.table;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobTableSchema;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created on 2018/11/27 10:37
 *
 * @author zhangchaozhou
 */
public class TableActivity extends AppCompatActivity {
    AppCompatButton mBtnTableSchema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_schema);
        mBtnTableSchema = findViewById(R.id.btn_table_schema);
    }


    /**
     * 获取指定表的表结构信息
     *
     * @return void
     * @throws
     * @method getTableSchema
     */
    public void getTableSchema(String tableName) {

        Bmob.getTableSchema(tableName, new QueryListener<BmobTableSchema>() {

            @Override
            public void done(BmobTableSchema schema, BmobException ex) {
                if (ex == null) {
                    Snackbar.make(mBtnTableSchema, "查询成功：" + schema.getClassName() + "-" + schema.getFields().toString(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", ex.toString());
                    Snackbar.make(mBtnTableSchema, ex.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 获取本应用下的所有表的表结构信息
     *
     * @return void
     * @throws
     * @method getAllTableSchema
     */
    public void onGetAllTableSchema(View view) {
        Bmob.getAllTableSchema(new QueryListListener<BmobTableSchema>() {

            @Override
            public void done(List<BmobTableSchema> schemas, BmobException ex) {
                if (ex == null) {
                    Snackbar.make(view, "查询成功：" + schemas.get(0).getClassName() + "---" + schemas.get(0).getFields().toString(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", ex.toString());
                    Snackbar.make(view, ex.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onTableSchema(View view) {
        getTableSchema("_User");
    }
}
