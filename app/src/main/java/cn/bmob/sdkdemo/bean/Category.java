package cn.bmob.sdkdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created on 2018/11/22 10:41
 *
 * @author zhangchaozhou
 */
public class Category extends BmobObject {


    /**
     * 类别名称
     */
    private String name;

    /**
     * 类别解释
     */
    private String desc;
    /**
     * 类别排名
     */
    private Integer sequence;


    private String hide;

    public String getHide() {
        return hide;
    }

    public Category setHide(String hide) {
        this.hide = hide;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Category setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public Category setSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }
}
