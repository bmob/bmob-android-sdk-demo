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
 * Created on 18/9/26 09:40
 * TODO 通过短信绑定或解绑手机号码，只针对已经通过其他方式注册的用户
 *
 * @author zhangchaozhou
 */
public class UserBindActivity extends AppCompatActivity {


    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bind);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_send, R.id.btn_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send: {
                User user = BmobUser.getCurrentUser(User.class);
                String phone = user.getMobilePhoneNumber();
                Boolean verify = user.getMobilePhoneNumberVerified();
                if (!TextUtils.isEmpty(phone) && verify != null && verify) {
                    Toast.makeText(this, "当前账号已经绑定了手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    final String phoneInput = mEdtPhone.getText().toString().trim();
                    if (TextUtils.isEmpty(phoneInput)) {
                        Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    /**
                     * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板，默认模板使用空字符串""。
                     */
                    BmobSMS.requestSMSCode(phoneInput, "DataSDK", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId, BmobException e) {
                            if (e == null) {
                                mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                            } else {
                                mTvInfo.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                            }
                        }
                    });
                }

                break;
            }
            case R.id.btn_bind: {
                final String phone = mEdtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            mTvInfo.append("验证码验证成功，您可以在此时进行绑定操作！\n");
                            User user = BmobUser.getCurrentUser(User.class);
                            user.setMobilePhoneNumber(phone);
                            user.setMobilePhoneNumberVerified(true);
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        mTvInfo.append("绑定手机号码成功");
                                    } else {
                                        mTvInfo.append("绑定手机号码失败：" + e.getErrorCode() + "-" + e.getMessage());
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
