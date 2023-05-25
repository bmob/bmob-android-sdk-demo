package cn.bmob.sdkdemo.activity.update;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobDialogButtonListener;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created on 2018/12/6 09:21
 *
 * @author zhangchaozhou
 */
public class AppVersionUpdateActivity extends AppCompatActivity {


    private static final int REQUEST_AUTO = 1001;
    private static final int REQUEST_CHECK = 1002;
    private static final int REQUEST_SILENT = 1003;
    private static final int REQUEST_DELETE = 1004;


    AppCompatButton mBtnAutoUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version_update);

        setTitle(".activity.update.AppVersionUpdateActivity");

        mBtnAutoUpdate = findViewById(R.id.btn_auto_update);

        //TODO 初始化，当控制台表出现后，注释掉此句
        BmobUpdateAgent.initAppVersion();
        //TODO 设置仅WiFi环境更新
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        //TODO 设置更新监听器
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                BmobException e = updateInfo.getException();
                if (e == null) {
                    Snackbar.make(mBtnAutoUpdate, "检测更新返回：" + updateInfo.version + "-" + updateInfo.path, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnAutoUpdate, "检测更新返回：" + e.getMessage() + "(" + e.getErrorCode() + ")", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        //TODO 设置对话框监听器
        BmobUpdateAgent.setDialogListener(new BmobDialogButtonListener() {

            @Override
            public void onClick(int status) {
                switch (status) {
                    case UpdateStatus.Update:
                        Snackbar.make(mBtnAutoUpdate, "点击了立即更新按钮", Snackbar.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.NotNow:
                        Snackbar.make(mBtnAutoUpdate, "点击了以后再说按钮", Snackbar.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.Close:
                        Snackbar.make(mBtnAutoUpdate, "点击了对话框关闭按钮", Snackbar.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }
            }
        });


    }

    public void onAutoUpdate(View view) {
        checkStoragePermissions(REQUEST_AUTO);
    }

    public void onCheckUpdate(View view) {
        checkStoragePermissions(REQUEST_CHECK);
    }

    public void onDownloadSilent(View view) {
        checkStoragePermissions(REQUEST_SILENT);
    }

    public void onDeleteApk(View view) {
        checkStoragePermissions(REQUEST_DELETE);
    }

    /**
     * 检查权限
     *
     * @param requestCode
     */
    public void checkStoragePermissions(int requestCode) {
        List<String> permissions = new ArrayList<>();
        int permissionCheckWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheckWrite != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        int permissionCheckRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) {
            String[] missions = new String[]{};
            ActivityCompat.requestPermissions(this, permissions.toArray(missions), requestCode);
        } else {
            switch (requestCode) {
                case REQUEST_AUTO:
                    BmobUpdateAgent.update(this);
                    break;
                case REQUEST_CHECK:
                    BmobUpdateAgent.update(this);
                    break;
                case REQUEST_SILENT:
                    BmobUpdateAgent.update(this);
                    break;
                case REQUEST_DELETE:
                    deleteApk();
                    break;
                default:
                    break;
            }
        }
    }

    private void deleteApk() {
        boolean isDelete = BmobUpdateAgent.deleteApk();
        if (isDelete) {
            Snackbar.make(mBtnAutoUpdate, "删除成功", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(mBtnAutoUpdate, "删除失败", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * 检查授权结果
     *
     * @param grantResults
     * @return
     */
    public boolean checkResults(int[] grantResults) {
        if (grantResults == null || grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUTO:
                if (checkResults(grantResults)) {
                    BmobUpdateAgent.update(this);
                }
                break;
            case REQUEST_CHECK:
                if (checkResults(grantResults)) {
                    BmobUpdateAgent.update(this);
                }
                break;
            case REQUEST_SILENT:
                if (checkResults(grantResults)) {
                    BmobUpdateAgent.update(this);
                }
                break;
            case REQUEST_DELETE:
                if (checkResults(grantResults)) {
                    deleteApk();
                }
                break;
            default:
                break;
        }
    }
}
