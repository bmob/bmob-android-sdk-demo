package cn.bmob.sdkdemo.activity.other;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.btn_reset_domain)
    AppCompatButton mBtnResetDomain;
    @BindView(R.id.btn_overseas_speedup)
    AppCompatButton mBtnOverseasSpeedup;
    @BindView(R.id.btn_server_time)
    AppCompatButton mBtnServerTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_function);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_reset_domain, R.id.btn_overseas_speedup, R.id.btn_server_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_domain:
                Snackbar.make(mBtnResetDomain, "见SDK初始化前操作", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn_overseas_speedup:
                Snackbar.make(mBtnResetDomain, "见SDK初始化前操作", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn_server_time:
                getServerTime();
                break;
            default:
                break;
        }
    }


    /**
     * 获取服务器时间
     */
    private void getServerTime() {
        Bmob.getServerTime(new QueryListener<Long>() {
            @Override
            public void done(Long time, BmobException e) {
                if (e == null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String times = formatter.format(new Date(time * 1000L));
                    Snackbar.make(mBtnServerTime, "当前服务器时间为：" + times, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnServerTime, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
