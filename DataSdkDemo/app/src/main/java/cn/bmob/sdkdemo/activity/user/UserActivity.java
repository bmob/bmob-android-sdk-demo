package cn.bmob.sdkdemo.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.activity.user.email.UserEmailActivity;
import cn.bmob.sdkdemo.activity.user.normal.UserNormalActivity;
import cn.bmob.sdkdemo.activity.user.sms.UserMainActivity;
import cn.bmob.sdkdemo.activity.user.third.UserThirdActivity;

/**
 *
 * 用户管理
 * @author zhangchaozhou
 */
public class UserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_normal, R.id.btn_sms, R.id.btn_email, R.id.btn_third})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_normal:
                //正常用户操作
                startActivity(new Intent(this, UserNormalActivity.class));
                break;
            case R.id.btn_sms:
                //用户的短信操作
                startActivity(new Intent(this, UserMainActivity.class));
                break;
            case R.id.btn_email:
                //用户的邮件操作
                startActivity(new Intent(this, UserEmailActivity.class));
                break;
            case R.id.btn_third:
                //用户的第三方操作
                startActivity(new Intent(this, UserThirdActivity.class));
                break;

            default:
                break;
        }
    }
}
