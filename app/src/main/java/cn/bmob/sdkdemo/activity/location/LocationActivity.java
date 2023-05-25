package cn.bmob.sdkdemo.activity.location;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.sdkdemo.R;
import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 地理位置
 *
 * @author zhangchaozhou
 */
public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

    /**
     * 查询最接近某个坐标的用户
     * @param view
     */
    public void onQueryNear(View view) {
        BmobQuery<User> query = new BmobQuery<>();
        BmobGeoPoint location = new BmobGeoPoint(112.934755, 24.52065);
        query.addWhereNear("address", location);
        query.setLimit(10);
        query.findObjects(new FindListener<User>() {

            @Override
            public void done(List<User> users, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + users.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 查询指定坐标指定半径内的用户
     */
    public void onQueryWithinRadians(View view) {
        BmobQuery<User> query = new BmobQuery<>();
        BmobGeoPoint address = new BmobGeoPoint(112.934755, 24.52065);
        query.addWhereWithinRadians("address", address, 10.0);
        query.findObjects(new FindListener<User>() {

            @Override
            public void done(List<User> users, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + users.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 查询指定坐标指定英里范围内的用户
     */
    public void onQueryWithinMiles(View view) {
        BmobQuery<User> query = new BmobQuery<>();
        BmobGeoPoint address = new BmobGeoPoint(112.934755, 24.52065);
        query.addWhereWithinMiles("address", address, 10.0);
        query.findObjects(new FindListener<User>() {

            @Override
            public void done(List<User> users, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + users.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 查询指定坐标指定公里范围内的用户
     */
    public void onQueryWithinKilometers(View view) {
        BmobQuery<User> query = new BmobQuery<>();
        BmobGeoPoint address = new BmobGeoPoint(112.934755, 24.52065);
        query.addWhereWithinKilometers("address", address, 10);
        query.findObjects(new FindListener<User>() {

            @Override
            public void done(List<User> users, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + users.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 查询矩形范围内的用户
     */
    public void onQueryBox(View view) {
        BmobQuery<User> query = new BmobQuery<>();
        //TODO 西南点，矩形的左下角坐标
        BmobGeoPoint southwestOfSF = new BmobGeoPoint(112.934755, 24.52065);
        //TODO 东别点，矩形的右上角坐标
        BmobGeoPoint northeastOfSF = new BmobGeoPoint(116.627623, 40.143687);
        query.addWhereWithinGeoBox("address", southwestOfSF, northeastOfSF);
        query.findObjects(new FindListener<User>() {

            @Override
            public void done(List<User> users, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "查询成功：" + users.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 获取当前用户的地理位置信息
     */
    public void onGetLocation(View view) {
        User user = BmobUser.getCurrentUser(User.class);
        if (user != null) {
            BmobGeoPoint address = user.getAddress();
            if (address==null){
                Snackbar.make(view, "地址不存在", Snackbar.LENGTH_LONG).show();
            }else {
                Snackbar.make(view, "查询成功：" + address.getLatitude() + "-" + address.getLongitude(), Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(view, "请先登录", Snackbar.LENGTH_LONG).show();
        }
    }


    /**
     * 更新当前用户地理位置信息
     */
    public void onUpdateLocation(View view) {
        //TODO 在实际应用中，此处利用实时定位替换为真实经纬度数据
        final BmobGeoPoint bmobGeoPoint = new BmobGeoPoint(116.39727786183357, 39.913768382429105);
        final User user = BmobUser.getCurrentUser(User.class);
        user.setAddress(bmobGeoPoint);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "更新成功：" + user.getAddress().getLatitude() + "-" + user.getAddress().getLongitude(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
