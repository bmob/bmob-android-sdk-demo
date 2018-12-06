package cn.bmob.sdkdemo.activity.installation;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.btn_installation_id)
    AppCompatButton mBtnInstallationId;
    @BindView(R.id.btn_installation_init)
    AppCompatButton mBtnInstallationInit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_installation_id, R.id.btn_installation_init})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_installation_id:

                Snackbar.make(mBtnInstallationId, "设备唯一号 " + BmobInstallationManager.getInstallationId(), Snackbar.LENGTH_LONG).show();

                break;
            case R.id.btn_installation_init:
                BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
                    @Override
                    public void done(BmobInstallation bmobInstallation, BmobException e) {
                        if (e == null) {
                            Snackbar.make(mBtnInstallationInit, "设备初始化成功：" + bmobInstallation.getObjectId(), Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mBtnInstallationInit, "设备初始化失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
