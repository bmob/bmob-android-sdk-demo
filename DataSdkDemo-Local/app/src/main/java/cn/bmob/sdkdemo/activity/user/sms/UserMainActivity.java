package cn.bmob.sdkdemo.activity.user.sms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobUser;

/**
 * Created on 18/9/26 09:52
 * TODO 用户登录后首页
 * @author zhangchaozhou
 */
public class UserMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_reset_password, R.id.btn_reset_sms, R.id.btn_bind, R.id.btn_unbind, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_password:
                startActivity(new Intent(this,UserResetPasswordActivity.class));
                break;
            case R.id.btn_reset_sms:
                startActivity(new Intent(this,UserResetSmsActivity.class));
                break;
            case R.id.btn_bind:
                startActivity(new Intent(this,UserBindActivity.class));
                break;
            case R.id.btn_unbind:
                startActivity(new Intent(this,UserUnBindActivity.class));
                break;
            case R.id.btn_exit:
                BmobUser.logOut();
                finish();
                break;
            default:
                break;
        }
    }
}
