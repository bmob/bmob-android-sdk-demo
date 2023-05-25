package cn.bmob.sdkdemo.activity.other;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.sdkdemo.R;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created on 2018/11/16 11:37
 *
 * @author zhangchaozhou
 */
public class OtherFunctionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_function);
    }

    public void onResetDomain(View view){
        Snackbar.make(view, "见SDK初始化前操作", Snackbar.LENGTH_LONG).show();
    }

    public void onOverseasSpeedup(View view){
        Snackbar.make(view, "见SDK初始化前操作", Snackbar.LENGTH_LONG).show();
    }

    /**
     * 获取服务器时间
     */
    public void onGetServerTime(View view) {
        Bmob.getServerTime(new QueryListener<Long>() {
            @Override
            public void done(Long time, BmobException e) {
                if (e == null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String times = formatter.format(new Date(time * 1000L));
                    Snackbar.make(view, "当前服务器时间为：" + times, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
