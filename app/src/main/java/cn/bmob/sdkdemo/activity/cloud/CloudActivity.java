package cn.bmob.sdkdemo.activity.cloud;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);
    }


    /**
     * 云端代码，不带参数
     */
    public void onCloudCode(View view) {
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        //不带请求参数的云端代码
        ace.callEndpoint("log", new CloudCodeListener() {

            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "执行成功：" + object.toString(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 云端代码，带参数
     */
    public void onCloudCodeParams(View view) {
        //带请求的云端代码
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        JSONObject params = new JSONObject();
        ace.callEndpoint("method", params, new CloudCodeListener() {

            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "执行成功：" + object.toString(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

}
