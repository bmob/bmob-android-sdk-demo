package cn.bmob.sdkdemo.activity.user.sms;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created on 18/9/26 09:39
 * TODO 通过密码登录，只针对已经通过密码注册的用户
 *
 * @author zhangchaozhou
 */
public class UserLoginPasswordActivity extends AppCompatActivity {
    EditText mEdtUsername;
    EditText mEdtPassword;
    TextView mTvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_password);

        mEdtUsername = findViewById(R.id.edt_username);
        mEdtPassword = findViewById(R.id.edt_password);
        mTvInfo = findViewById(R.id.tv_info);
    }

    public void onLogin(View view) {
        String username = mEdtUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = mEdtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    mTvInfo.append("登录成功：" + user.getObjectId());
                    startActivity(new Intent(UserLoginPasswordActivity.this, UserMainActivity.class));
                } else {
                    mTvInfo.append("登录失败：" + e.getMessage());
                }
            }
        });
    }
}
