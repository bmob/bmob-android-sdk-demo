package cn.bmob.sdkdemo.activity.user.sms;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 18/9/26 09:40
 * TODO 通过短信绑定或解绑手机号码，只针对已经通过其他方式注册的用户
 *
 * @author zhangchaozhou
 */
public class UserUnBindActivity extends AppCompatActivity {

    EditText mEdtCode;
    TextView mTvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_unbind);
        mEdtCode = findViewById(R.id.edt_code);
        mTvInfo = findViewById(R.id.tv_info);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send: {
                User user = BmobUser.getCurrentUser(User.class);
                String phone = user.getMobilePhoneNumber();
                Boolean verify = user.getMobilePhoneNumberVerified();
                if (TextUtils.isEmpty(phone) || verify == null || !verify) {
                    Toast.makeText(this, "当前账号尚未绑定手机号码", Toast.LENGTH_SHORT).show();
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
            case R.id.btn_unbind: {
                User user = BmobUser.getCurrentUser(User.class);
                final String phone = user.getMobilePhoneNumber();
                String code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            mTvInfo.append("验证码验证成功，您可以在此时进行解绑操作！\n");
                            User user = BmobUser.getCurrentUser(User.class);
                            user.setMobilePhoneNumber("");
                            user.setMobilePhoneNumberVerified(false);
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        mTvInfo.append("解绑手机号码成功");
                                    } else {
                                        mTvInfo.append("解绑手机号码失败：" + e.getErrorCode() + "-" + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            mTvInfo.append("验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
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
