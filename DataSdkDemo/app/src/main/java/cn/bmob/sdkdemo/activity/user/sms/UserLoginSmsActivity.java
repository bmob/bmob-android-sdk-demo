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
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created on 18/9/26 09:39
 * TODO 通过短信登录，只针对已经通过短信验证注册的用户
 *
 * @author zhangchaozhou
 */
public class UserLoginSmsActivity extends AppCompatActivity {
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_sms);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_send, R.id.btn_login})
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
            case R.id.btn_login: {
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
                /**
                 * TODO 此API需要在用户已经注册并验证的前提下才能使用
                 */
                BmobUser.loginBySMSCode(phone, code, new LogInListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            mTvInfo.append("短信登录成功：" + bmobUser.getObjectId() + "-" + bmobUser.getUsername());
                            startActivity(new Intent(UserLoginSmsActivity.this, UserMainActivity.class));
                        } else {
                            mTvInfo.append("短信登录失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        }
                    }
                });
                break;
            }
            default:
                break;
        }
    }


    /**
     * 手机号码密码登录
     */
    private void loginByPhone(){
        //TODO 此处替换为你的手机号码和密码
        BmobUser.loginByAccount("phone", "password", new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(user!=null){
                    if (e == null) {
                        mTvInfo.append("短信登录成功：" + user.getObjectId() + "-" + user.getUsername());
                    } else {
                        mTvInfo.append("短信登录失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                    }
                }
            }
        });
    }

}
