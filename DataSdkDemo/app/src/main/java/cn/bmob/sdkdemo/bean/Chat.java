package cn.bmob.sdkdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created on 18/9/19 09:41
 *
 * @author zhangchaozhou
 */
public class Chat extends BmobObject {
    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public Chat setName(String name) {
        this.name = name;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Chat setContent(String content) {
        this.content = content;
        return this;
    }
}
