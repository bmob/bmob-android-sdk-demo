package cn.bmob.sdkdemo.activity.security.role;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRole;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created on 2018/11/26 15:13
 *
 * @author zhangchaozhou
 */
public class BmobRoleActivity extends AppCompatActivity {

    @BindView(R.id.btn_query_role)
    AppCompatButton mBtnQueryRole;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_query_role, R.id.btn_add_role, R.id.btn_remove_role})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query_role:
                queryRole("female");
                break;
            case R.id.btn_add_role: {
                BmobRole bmobRole = new BmobRole("female");
                saveRoleAndAddUser2Role(bmobRole);
            }
            break;
            case R.id.btn_remove_role: {
                BmobRole bmobRole = new BmobRole("female");
                removeUserFromRole(bmobRole);
            }
            break;

            default:

                break;
        }
    }


    /**
     * 查询某角色是否存在
     *
     * @param roleName
     */
    private void queryRole(final String roleName) {
        BmobQuery<BmobRole> bmobRoleBmobQuery = new BmobQuery<>();
        bmobRoleBmobQuery.addWhereEqualTo("name", roleName);
        bmobRoleBmobQuery.findObjects(new FindListener<BmobRole>() {
            @Override
            public void done(List<BmobRole> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        //已存在该角色
                        addUser2Role(list.get(0));
                    } else {
                        //不存在该角色
                        BmobRole bmobRole = new BmobRole(roleName);
                        saveRoleAndAddUser2Role(bmobRole);
                    }
                } else {
                    Snackbar.make(mBtnQueryRole, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }


    /**
     * 保存某个角色并保存用户到该角色中
     *
     * @param bmobRole
     */
    private void saveRoleAndAddUser2Role(BmobRole bmobRole) {

        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            Snackbar.make(mBtnQueryRole, "请先登录", Snackbar.LENGTH_LONG).show();
        } else {
            bmobRole.getUsers().add(user);
            bmobRole.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(BmobRoleActivity.this, "角色用户添加成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(mBtnQueryRole, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    /**
     * 添加用户到某个角色中
     *
     * @param bmobRole
     */
    private void addUser2Role(BmobRole bmobRole) {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            Snackbar.make(mBtnQueryRole, "请先登录", Snackbar.LENGTH_LONG).show();
        } else {
            bmobRole.getUsers().add(user);
            bmobRole.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(BmobRoleActivity.this, "角色用户添加成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BmobRoleActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    /**
     * 把用户从某个角色中移除
     *
     * @param bmobRole
     */
    private void removeUserFromRole(BmobRole bmobRole) {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            Snackbar.make(mBtnQueryRole, "请先登录", Snackbar.LENGTH_LONG).show();
        } else {
            bmobRole.getUsers().remove(user);
            bmobRole.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(BmobRoleActivity.this, "角色用户添加成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BmobRoleActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
