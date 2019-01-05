package cn.bmob.sdkdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.activity.array.ArrayActivity;
import cn.bmob.sdkdemo.activity.article.ArticleActivity;
import cn.bmob.sdkdemo.activity.cloud.CloudActivity;
import cn.bmob.sdkdemo.activity.date.DateActivity;
import cn.bmob.sdkdemo.activity.file.FileManagerActivity;
import cn.bmob.sdkdemo.activity.installation.InstallationActivity;
import cn.bmob.sdkdemo.activity.location.LocationActivity;
import cn.bmob.sdkdemo.activity.object.DataOperationActivity;
import cn.bmob.sdkdemo.activity.other.OtherFunctionActivity;
import cn.bmob.sdkdemo.activity.realtime.RealTimeDataActivity;
import cn.bmob.sdkdemo.activity.relevance.PostsActivity;
import cn.bmob.sdkdemo.activity.security.SecurityActivity;
import cn.bmob.sdkdemo.activity.sms.SmsActivity;
import cn.bmob.sdkdemo.activity.table.TableActivity;
import cn.bmob.sdkdemo.activity.update.AppVersionUpdateActivity;
import cn.bmob.sdkdemo.activity.user.UserActivity;

/**
 * 数据SDK 操作示例
 *
 * @author zhangchaozhou
 */
public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_user)
    AppCompatButton mBtnUser;
    @BindView(R.id.btn_installation)
    AppCompatButton mBtnInstallation;
    @BindView(R.id.btn_file)
    AppCompatButton mBtnFile;
    @BindView(R.id.btn_relevance)
    AppCompatButton mBtnRelevance;
    @BindView(R.id.btn_security)
    AppCompatButton mBtnSecurity;
    @BindView(R.id.btn_realtime)
    AppCompatButton mBtnRealtime;
    @BindView(R.id.btn_cloud)
    AppCompatButton mBtnCloud;
    @BindView(R.id.btn_update)
    AppCompatButton mBtnUpdate;
    @BindView(R.id.btn_article)
    AppCompatButton mBtnArticle;
    @BindView(R.id.btn_location)
    AppCompatButton mBtnLocation;
    @BindView(R.id.btn_array)
    AppCompatButton mBtnArray;
    @BindView(R.id.btn_date)
    AppCompatButton mBtnDate;
    @BindView(R.id.btn_table)
    AppCompatButton mBtnTable;
    @BindView(R.id.btn_other)
    AppCompatButton mBtnOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_data_operation, R.id.btn_user, R.id.btn_installation, R.id.btn_file, R.id.btn_relevance,
            R.id.btn_security, R.id.btn_realtime, R.id.btn_cloud, R.id.btn_update, R.id.btn_article,
            R.id.btn_location, R.id.btn_array, R.id.btn_date, R.id.btn_table, R.id.btn_other, R.id.btn_sms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_data_operation:
                startActivity(new Intent(this, DataOperationActivity.class));
                break;
            case R.id.btn_user:
                startActivity(new Intent(this, UserActivity.class));
                break;
            case R.id.btn_installation:
                startActivity(new Intent(this, InstallationActivity.class));
                break;
            case R.id.btn_file:
                startActivity(new Intent(this, FileManagerActivity.class));
                break;
            case R.id.btn_relevance:
                startActivity(new Intent(this, PostsActivity.class));
                break;
            case R.id.btn_security:
                startActivity(new Intent(this, SecurityActivity.class));
                break;
            case R.id.btn_realtime:
                startActivity(new Intent(this, RealTimeDataActivity.class));
                break;
            case R.id.btn_cloud:
                startActivity(new Intent(this, CloudActivity.class));
                break;
            case R.id.btn_update:
                startActivity(new Intent(this,AppVersionUpdateActivity.class));
                break;
            case R.id.btn_article:
                startActivity(new Intent(this, ArticleActivity.class));
                break;
            case R.id.btn_location:
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.btn_array:
                startActivity(new Intent(this, ArrayActivity.class));
                break;
            case R.id.btn_date:
                startActivity(new Intent(this, DateActivity.class));
                break;
            case R.id.btn_table:
                startActivity(new Intent(this, TableActivity.class));
                break;
            case R.id.btn_sms:
                startActivity(new Intent(this, SmsActivity.class));
                break;
            case R.id.btn_other:
                startActivity(new Intent(this, OtherFunctionActivity.class));
                break;
            default:
                break;
        }
    }
}
