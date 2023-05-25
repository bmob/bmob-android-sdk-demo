package cn.bmob.sdkdemo;

/**
 * Created on 2020/5/7 09:22
 *
 * @author zhangchaozhou
 * @email 13760289294@139.com
 * @wechat 13760289294
 */
public class Test2 extends Test1 {
    private String test;
    private String name;

    public String getName() {
        return name;
    }

    public Test2 setName(String name) {
        this.name = name;
        return this;
    }

    public String getTest() {
        return test;
    }

    public Test2 setTest(String test) {
        this.test = test;
        return this;
    }
}
