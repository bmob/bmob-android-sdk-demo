package cn.bmob.sdkdemo.activity.user.third;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 2018/11/27 14:40
 *
 * @author zhangchaozhou
 */
public class UserThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_third);
    }

    /**
     * 第三方平台一键注册或登录
     *
     * @param snsType
     * @param accessToken
     * @param expiresIn
     * @param userId
     */
    private void thirdSingupLogin(View view, String snsType, String accessToken, String expiresIn, String userId) {
        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId);
        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {
            @Override
            public void done(JSONObject user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "第三方平台一键注册或登录成功", Snackbar.LENGTH_LONG).show();
                    BmobUser.fetchUserInfo(new FetchUserInfoListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                User currentUser = BmobUser.getCurrentUser(User.class);
                                Log.d("bmob", currentUser.getNickname());
                            } else {
                                Log.e("bmob", e.getMessage());
                            }
                        }
                    });
                } else {
                    Log.e("bmob", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 第三方平台关联
     *
     * @param snsType
     * @param accessToken
     * @param expiresIn
     * @param userId
     */
    private void associate(View view, String snsType, String accessToken, String expiresIn, String userId) {
        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId);
        BmobUser.associateWithAuthData(authInfo, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "第三方平台关联成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 解除第三方平台关联
     *
     * @param snsType
     */
    private void unAssociate(View view, String snsType) {
        BmobUser.dissociateAuthData(snsType, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "第三方平台关联解除成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    if (e.getErrorCode() == 208) {
                        Snackbar.make(view, "你没有关联该账号", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_third_signup_login:
                //TODO 此处替换为你进行第三方登录后得到的信息
                /**
                 * {"weixin":{"access_token":"19_bR0avZItt0P2AXxmrraVJaz0ga5nsdRadQ-yQK3wtbsLfYfd-2taTMOynzcdNmiBUaKKs4zqw9KAsIE8877MWCDumejjvQyuuZ2-Rwy7dx0",
                 * "expires_in":1553073261144,"openid":"ol95a09vOEMWaL29DHMSajqhrpg0"}}
                 */
                thirdSingupLogin(view, "weixin",
                        "19_bR0avZItt0P2AXxmrraVJaz0ga5nsdRadQ-yQK3wtbsLfYfd-2taTMOynzcdNmiBUaKKs4zqw9KAsIE8877MWCDumejjvQyuuZ2-Rwy7dx0",
                        "1553073261144", "ol95a09vOEMWaL29DHMSajqhrpg0");
                break;
            case R.id.btn_third_bind:
                //TODO 此处替换为你进行第三方登录后得到的信息
                associate(view, "", "", "", "");
                break;
            case R.id.btn_third_unbind:
                unAssociate(view, "");
                break;
            default:
                break;
        }
    }


}
