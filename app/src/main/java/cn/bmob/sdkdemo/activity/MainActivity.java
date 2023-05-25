package cn.bmob.sdkdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.Test2;
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
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 数据SDK 操作示例
 *
 * @author zhangchaozhou
 */
public class MainActivity extends AppCompatActivity {

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSecretJava(View view) {
        Test2 test2 = new Test2();
        test2.setName("zhang");
        test2.setSex(1);
        test2.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    System.out.println(s);
                } else {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    public void toDataOperationActivity(View view) {
        startActivity(new Intent(this, DataOperationActivity.class));
    }

    public void toUserActivity(View view) {
        startActivity(new Intent(this, UserActivity.class));
    }

    public void toInstallationActivity(View view) {
        startActivity(new Intent(this, InstallationActivity.class));
    }

    public void toFileManagerActivity(View view) {
        startActivity(new Intent(this, FileManagerActivity.class));
    }

    public void toPostsActivity(View view) {
        startActivity(new Intent(this, PostsActivity.class));
    }

    public void toSecurityActivity(View view) {
        startActivity(new Intent(this, SecurityActivity.class));
    }

    public void toRealTimeDataActivity(View view) {
        startActivity(new Intent(this, RealTimeDataActivity.class));
    }

    public void toCloudActivity(View view) {
        startActivity(new Intent(this, CloudActivity.class));
    }

    public void toAppVersionUpdateActivity(View view) {
        startActivity(new Intent(this, AppVersionUpdateActivity.class));
    }

    public void toArticleActivity(View view) {
        startActivity(new Intent(this, ArticleActivity.class));
    }

    public void toLocationActivity(View view) {
        startActivity(new Intent(this, LocationActivity.class));
    }

    public void toArrayActivity(View view) {
        startActivity(new Intent(this, ArrayActivity.class));
    }

    public void toDateActivity(View view) {
        startActivity(new Intent(this, DateActivity.class));
    }

    public void toTableActivity(View view) {
        startActivity(new Intent(this, TableActivity.class));
    }

    public void toSmsActivity(View view) {
        startActivity(new Intent(this, SmsActivity.class));
    }

    public void toOtherFunctionActivity(View view) {
        startActivity(new Intent(this, OtherFunctionActivity.class));
    }

}
