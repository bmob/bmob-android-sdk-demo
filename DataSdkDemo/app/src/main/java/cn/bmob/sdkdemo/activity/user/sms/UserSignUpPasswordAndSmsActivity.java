package cn.bmob.sdkdemo.activity.user.sms;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created on 18/9/25 16:23
 * TODO 通过密码注册并通过短信验证绑定手机号码
 * @author zhangchaozhou
 */
public class UserSignUpPasswordAndSmsActivity extends AppCompatActivity {


    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.edt_nickname)
    EditText mEdtNickname;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup_password_and_sms);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.btn_send, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send: {
                String phone = mEdtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板。
                 */
                BmobSMS.requestSMSCode(phone, "DataSDK", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                        } else {
                            mTvInfo.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        }
                    }
                });
                break;
            }
            case R.id.btn_signup: {
                String username = mEdtUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                String nickname = mEdtNickname.getText().toString().trim();
                if (TextUtils.isEmpty(nickname)) {
                    Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = mEdtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phone = mEdtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                //TODO 设置手机号码（必填）
                user.setMobilePhoneNumber(phone);
                //TODO 设置用户名，如果没有传用户名，则默认为手机号码
                user.setUsername(username);
                //TODO 设置用户密码
                user.setPassword(password);
                //TODO 设置额外信息：此处为昵称
                user.setNickname(nickname);
                user.signOrLogin(code, new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            mTvInfo.append("短信注册成功：" + bmobUser.getUsername());
                            startActivity(new Intent(UserSignUpPasswordAndSmsActivity.this, UserMainActivity.class));
                        } else {
                            mTvInfo.append("短信注册失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        }
                    }
                });
                break;
            }
            default:
                break;
        }
    }
}
