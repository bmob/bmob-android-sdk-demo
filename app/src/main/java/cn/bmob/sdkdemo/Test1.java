package cn.bmob.sdkdemo;

import cn.bmob.v3.BmobObject;

/**
 * Created on 2020/5/7 09:22
 *
 * @author zhangchaozhou
 * @email 13760289294@139.com
 * @wechat 13760289294
 */
public class Test1 extends BmobObject {
    private Integer sex;

    public Integer getSex() {
        return sex;
    }

    public Test1 setSex(Integer sex) {
        this.sex = sex;
        return this;
    }
}
