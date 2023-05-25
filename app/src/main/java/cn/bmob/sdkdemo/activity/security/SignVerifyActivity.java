package cn.bmob.sdkdemo.activity.security;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import cn.bmob.sdkdemo.R;
import cn.bmob.v3.util.AppUtils;

/**
 * Created on 2018/11/27 10:34
 *
 * @author zhangchaozhou
 */
public class SignVerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_verify);
    }

    public void onViewClicked(View view) {
        String sign = AppUtils.getSignature(this);
        Log.e("sign", sign);
        Snackbar.make(view, sign, Snackbar.LENGTH_LONG).show();
    }
}
