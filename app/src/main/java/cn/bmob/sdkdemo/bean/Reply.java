package cn.bmob.sdkdemo.bean;

import cn.bmob.v3.BmobObject;

public class Reply extends BmobObject {
    private String imei, imsi, username;
    private String permissionname;
    private String bt;
    private String nr;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }

    public String getBT() {
        return bt;
    }

    public void setBT(String bt) {
        this.bt = bt;
    }


    public String getNR() {
        return nr;
    }

    public void setNR(String nr) {
        this.nr = nr;
    }

}
