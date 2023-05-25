package cn.bmob.sdkdemo.activity.user.email;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 2018/11/27 14:39
 *
 * @author zhangchaozhou
 */
public class UserEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_email);
    }


    /**
     * 邮箱重置密码
     */
    private void resetPasswordByEmail(View view) {
        //TODO 此处替换为你的邮箱
        final String email = "email";
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "重置密码请求成功，请到" + email + "邮箱进行密码重置操作", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 发送验证邮件
     */
    private void emailVerify(View view) {
        //TODO 此处替换为你的邮箱
        final String email = "email";
        BmobUser.requestEmailVerify(email, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "请求验证邮件成功，请到" + email + "邮箱中进行激活账户。", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 邮箱+密码登录
     */
    private void loginByEmailPwd(View view) {
        //TODO 此处替换为你的邮箱和密码
        BmobUser.loginByAccount("1769379819@139.com", "1", new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, user.getUsername() + "-" + user.getAge() + "-" + user.getObjectId() + "-" + user.getEmail(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_password:
                resetPasswordByEmail(view);
                break;
            case R.id.btn_verify:
                emailVerify(view);
                break;
            case R.id.btn_login:
                loginByEmailPwd(view);
                break;
            default:
                break;
        }
    }
}
