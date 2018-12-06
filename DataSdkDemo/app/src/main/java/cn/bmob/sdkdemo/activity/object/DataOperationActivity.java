package cn.bmob.sdkdemo.activity.object;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;

/**
 * Created on 2018/12/5 14:51
 *
 * @author zhangchaozhou
 */
public class DataOperationActivity extends AppCompatActivity {

    @BindView(R.id.btn_single_curd)
    AppCompatButton mBtnSingleCurd;
    @BindView(R.id.btn_multi_crud)
    AppCompatButton mBtnMultiCrud;
    @BindView(R.id.btn_where)
    AppCompatButton mBtnWhere;
    @BindView(R.id.btn_regex)
    AppCompatButton mBtnRegex;
    @BindView(R.id.btn_statistics)
    AppCompatButton mBtnStatistics;
    @BindView(R.id.btn_bql)
    AppCompatButton mBtnBql;
    @BindView(R.id.btn_table)
    AppCompatButton mBtnTable;
    @BindView(R.id.btn_cache)
    AppCompatButton mBtnCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_operation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_single_curd, R.id.btn_multi_crud, R.id.btn_where, R.id.btn_regex, R.id.btn_statistics, R.id.btn_bql, R.id.btn_table, R.id.btn_cache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_single_curd:
                startActivity(new Intent(this,SingleCrudActivity.class));
                break;
            case R.id.btn_multi_crud:
                startActivity(new Intent(this,MultiCrudActivity.class));
                break;
            case R.id.btn_where:
                startActivity(new Intent(this,QueryWhereActivity.class));
                break;
            case R.id.btn_regex:
                startActivity(new Intent(this,QueryRegexActivity.class));
                break;
            case R.id.btn_statistics:
                startActivity(new Intent(this,QueryStatisticActivity.class));
                break;
            case R.id.btn_bql:
                startActivity(new Intent(this,QueryBqlActivity.class));
                break;
            case R.id.btn_table:
                startActivity(new Intent(this,QueryTableActivity.class));
                break;
            case R.id.btn_cache:
                startActivity(new Intent(this,QueryCacheActivity.class));
                break;

            default:
                break;
        }
    }
}
