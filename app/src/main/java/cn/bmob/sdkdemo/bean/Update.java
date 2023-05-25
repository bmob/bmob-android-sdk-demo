package cn.bmob.sdkdemo.bean;

import cn.bmob.v3.BmobObject;

public class Update extends BmobObject {
    private String imei, imsi;

    private String version;
    private String updatelog;
    private String color;


    public String getVersion() {
        return version;
    }

    public String getUpdateLog() {
        return updatelog;
    }

    public void setUpdateLog(String updatelog) {
        this.updatelog = updatelog;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getColor() {
        return color;
    }


    public String getImei() {
        return imei;
    }

    public String getImsi() {
        return imsi;
    }


}


