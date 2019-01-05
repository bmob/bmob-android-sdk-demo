package cn.bmob.sdkdemo.activity.cloud;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * Created on 2018/11/27 11:05
 * 云端代码
 *
 * @author zhangchaozhou
 */
public class CloudActivity extends AppCompatActivity {
    @BindView(R.id.btn_cloud)
    AppCompatButton mBtnCloud;
    @BindView(R.id.btn_cloud_params)
    AppCompatButton mBtnCloudParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);
        ButterKnife.bind(this);
    }


    /**
     * 云端代码，不带参数
     */
    private void cloudCode() {
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        //不带请求参数的云端代码
        ace.callEndpoint("method", new CloudCodeListener() {

            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnCloud, "执行成功：" + object.toString(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnCloud, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 云端代码，带参数
     */
    private void cloudCodeParams() {
        //带请求的云端代码
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        JSONObject params = new JSONObject();
        ace.callEndpoint("method", params, new CloudCodeListener() {

            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnCloudParams, "执行成功：" + object.toString(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mBtnCloudParams, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    @OnClick({R.id.btn_cloud, R.id.btn_cloud_params})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cloud:
                cloudCode();
                break;
            case R.id.btn_cloud_params:
                cloudCodeParams();
                break;
            default:
                break;
        }
    }
}
