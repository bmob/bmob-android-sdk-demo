package cn.bmob.sdkdemo.activity.user.sms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobUser;

/**
 * Created on 18/9/26 09:52
 * TODO 用户登录后首页
 *
 * @author zhangchaozhou
 */
public class UserMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_password:
                startActivity(new Intent(this, UserResetPasswordActivity.class));
                break;
            case R.id.btn_reset_sms:
                startActivity(new Intent(this, UserResetSmsActivity.class));
                break;
            case R.id.btn_bind:
                startActivity(new Intent(this, UserBindActivity.class));
                break;
            case R.id.btn_unbind:
                startActivity(new Intent(this, UserUnBindActivity.class));
//                startActivity(new Intent(this,UserSignUpPasswordAndSmsActivity.class));
                break;
            case R.id.btn_exit:
                BmobUser.logOut();
                finish();
//                startActivity(new Intent(this,UserSignUpOrLoginSmsActivity.class));
                break;
            default:
                break;
        }
    }
}
