package cn.bmob.sdkdemo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created on 2019-06-04 15:29
 *
 * @author zhangchaozhou
 * @email 13760289294@139.com
 * @wechat 13760289294
 */
public class sensorcontroller extends BmobObject {
    String Name;
    Double SensorData;
    Boolean ControllerData;
    Integer Factory;
    Boolean SorC;
    String SensorType;
    BmobFile Icon;

    public BmobFile getIcon() {
        return Icon;
    }

    public void setIcon(BmobFile icon) {
        Icon = icon;
    }

    public String getSensorType() {
        return SensorType;
    }

    public void setSensorType(String sensorType) {
        SensorType = sensorType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getSensorData() {
        return SensorData;
    }

    public void setSensorData(Double sensorData) {
        SensorData = sensorData;
    }

    public Boolean getControllerData() {
        return ControllerData;
    }

    public void setControllerData(Boolean controllerData) {
        ControllerData = controllerData;
    }

    public Integer getFactory() {
        return Factory;
    }

    public void setFactory(Integer factory) {
        Factory = factory;
    }

    public Boolean getSorC() {
        return SorC;
    }

    public void setSorC(Boolean sorC) {
        SorC = sorC;
    }
}
