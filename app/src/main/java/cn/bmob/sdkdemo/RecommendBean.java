package cn.bmob.sdkdemo;

import cn.bmob.v3.BmobObject;

/**
 * Created on 2019-12-12 13:40
 *
 * @author zhangchaozhou
 * @email 13760289294@139.com
 * @wechat 13760289294
 */
public class RecommendBean extends BmobObject {
    private Integer type;
    private String typeName;
    private String classifyIcon;
    private String classifyName;

    public Integer getType() {
        return type;
    }

    public RecommendBean setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getClassifyIcon() {
        return classifyIcon;
    }

    public void setClassifyIcon(String classifyIcon) {
        this.classifyIcon = classifyIcon;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
