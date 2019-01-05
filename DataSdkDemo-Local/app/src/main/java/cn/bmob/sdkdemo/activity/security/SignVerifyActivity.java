package cn.bmob.sdkdemo.activity.security;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.v3.util.AppUtils;

/**
 * Created on 2018/11/27 10:34
 *
 * @author zhangchaozhou
 */
public class SignVerifyActivity extends AppCompatActivity {

    @BindView(R.id.btn_get_sign)
    AppCompatButton mBtnGetSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_verify);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get_sign)
    public void onViewClicked() {
        String sign = AppUtils.getSignature(this);
        Log.e("sign", sign);
        Snackbar.make(mBtnGetSign, sign, Snackbar.LENGTH_LONG).show();
    }
}
