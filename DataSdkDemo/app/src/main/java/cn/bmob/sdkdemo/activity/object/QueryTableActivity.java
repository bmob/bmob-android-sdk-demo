package cn.bmob.sdkdemo.activity.object;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created on 2018/11/23 14:48
 *
 * @author zhangchaozhou
 */
public class QueryTableActivity extends AppCompatActivity {
    @BindView(R.id.btn_query_table_object)
    AppCompatButton mBtnQueryTableObject;
    @BindView(R.id.btn_query_table_array)
    AppCompatButton mBtnQueryTableArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_table);
        ButterKnife.bind(this);
    }


    /**
     * 根据表名查询多条数据
     */
    public void queryArrayByTable() {
        BmobQuery query = new BmobQuery("_User");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray ary, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnQueryTableArray, "注册成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnQueryTableArray, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }

    /**
     * 根据表名查询单条数据
     */
    public void queryObjectByTable() {
        BmobQuery query = new BmobQuery("_User");
        query.getObjectByTable("此处替换为objectId", new QueryListener<JSONObject>() {
            @Override
            public void done(JSONObject jsonObject, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnQueryTableObject, "注册成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnQueryTableObject, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick({R.id.btn_query_table_object, R.id.btn_query_table_array})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query_table_object:
                queryObjectByTable();
                break;
            case R.id.btn_query_table_array:
                queryArrayByTable();
                break;

            default:
                break;
        }
    }
}
