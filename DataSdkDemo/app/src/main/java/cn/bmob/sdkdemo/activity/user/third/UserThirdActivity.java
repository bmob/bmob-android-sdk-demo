package cn.bmob.sdkdemo.activity.user.third;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 2018/11/27 14:40
 *
 * @author zhangchaozhou
 */
public class UserThirdActivity extends AppCompatActivity {

    @BindView(R.id.btn_third_signup_login)
    AppCompatButton mBtnThirdSignupLogin;
    @BindView(R.id.btn_third_bind)
    AppCompatButton mBtnThirdBind;
    @BindView(R.id.btn_third_unbind)
    AppCompatButton mBtnThirdUnbind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_third);
        ButterKnife.bind(this);


    }


    /**
     * 第三方平台一键注册或登录
     *
     * @param snsType
     * @param accessToken
     * @param expiresIn
     * @param userId
     */
    private void thirdSingupLogin(String snsType, String accessToken, String expiresIn, String userId) {
        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId);
        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {
            @Override
            public void done(JSONObject user, BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnThirdSignupLogin, "第三方平台一键注册或登录成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnThirdSignupLogin, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 第三方平台关联
     * @param snsType
     * @param accessToken
     * @param expiresIn
     * @param userId
     */
    private void associate(String snsType, String accessToken, String expiresIn, String userId){
        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(snsType,accessToken, expiresIn, userId);
        BmobUser.associateWithAuthData(authInfo, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnThirdSignupLogin, "第三方平台关联成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(mBtnThirdSignupLogin, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }




    /**
     * 解除第三方平台关联
     * @param snsType
     */
    private void unAssociate(String snsType) {
        BmobUser.dissociateAuthData(snsType,new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(mBtnThirdSignupLogin, "第三方平台关联解除成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    if (e.getErrorCode()==208){
                        Snackbar.make(mBtnThirdSignupLogin, "你没有关联该账号", Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(mBtnThirdSignupLogin, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @OnClick({R.id.btn_third_signup_login, R.id.btn_third_bind, R.id.btn_third_unbind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_third_signup_login:
                //TODO 此处替换为你进行第三方登录后得到的信息
                thirdSingupLogin("", "", "", "");
                break;
            case R.id.btn_third_bind:
                //TODO 此处替换为你进行第三方登录后得到的信息
                associate("", "", "", "");
                break;
            case R.id.btn_third_unbind:
                unAssociate("");
                break;
            default:
                break;
        }
    }


}
