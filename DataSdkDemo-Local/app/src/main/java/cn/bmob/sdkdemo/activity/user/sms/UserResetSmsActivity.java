package cn.bmob.sdkdemo.activity.user.sms;

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
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 18/9/26 09:39
 * TODO 通过短信重置密码，只针对已经通过密码注册并且通过短信验证绑定的用户
 *
 * @author zhangchaozhou
 */
public class UserResetSmsActivity extends AppCompatActivity {

    @BindView(R.id.edt_new_password)
    EditText mEdtNewPassword;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_sms);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_send, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                User user = BmobUser.getCurrentUser(User.class);
                String phone = user.getMobilePhoneNumber();
                Boolean verify = user.getMobilePhoneNumberVerified();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请先绑定手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (verify == null || !verify) {
                    Toast.makeText(this, "请先绑定手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板，模板名称为空字符串""。
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
            case R.id.btn_reset:
                String newPassword = mEdtNewPassword.getText().toString().trim();
                if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobUser.resetPasswordBySMSCode(code, newPassword, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            mTvInfo.append("重置成功");
                        } else {
                            mTvInfo.append("重置失败：" + e.getErrorCode() + "-" + e.getMessage());
                        }
                    }
                });
                break;

            default:
                break;
        }
    }
}
