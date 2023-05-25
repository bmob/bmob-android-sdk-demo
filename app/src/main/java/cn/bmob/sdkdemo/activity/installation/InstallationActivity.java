package cn.bmob.sdkdemo.activity.installation;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created on 2018/11/26 15:34
 *
 * @author zhangchaozhou
 */
public class InstallationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation);
    }

    public void onInstallationId(View view) {
        Snackbar.make(view, "设备唯一号 " + BmobInstallationManager.getInstallationId(), Snackbar.LENGTH_LONG).show();
    }

    public void onInstallationInit(View view) {
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "设备初始化成功：" + bmobInstallation.getObjectId(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "设备初始化失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
