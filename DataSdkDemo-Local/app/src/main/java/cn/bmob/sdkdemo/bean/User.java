package cn.bmob.sdkdemo.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created on 2018/11/22 18:01
 *
 * @author zhangchaozhou
 */
public class User extends BmobUser {


    /**
     * 昵称
     */
    private String nickname;

    /**
     * 国家
     */

    private String country;

    /**
     * 得分数
     */
    private Integer score;


    /**
     * 抢断次数
     */
    private Integer steal;


    /**
     * 犯规次数
     */
    private Integer foul;


    /**
     * 失误个数
     */
    private Integer fault;


    /**
     * 年龄
     */
    private Integer age;


    /**
     * 性别
     */
    private Integer gender;


    /**
     * 用户当前位置
     */
    private BmobGeoPoint address;


    /**
     * 头像
     */
    private BmobFile avatar;


    /**
     * 别名
     */
    private List<String> alias;


    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public User setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getSteal() {
        return steal;
    }

    public User setSteal(Integer steal) {
        this.steal = steal;
        return this;
    }

    public Integer getFoul() {
        return foul;
    }

    public User setFoul(Integer foul) {
        this.foul = foul;
        return this;
    }

    public Integer getFault() {
        return fault;
    }

    public User setFault(Integer fault) {
        this.fault = fault;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public User setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public BmobGeoPoint getAddress() {
        return address;
    }

    public User setAddress(BmobGeoPoint address) {
        this.address = address;
        return this;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public User setAvatar(BmobFile avatar) {
        this.avatar = avatar;
        return this;
    }

    public List<String> getAlias() {
        return alias;
    }

    public User setAlias(List<String> alias) {
        this.alias = alias;
        return this;
    }
}
