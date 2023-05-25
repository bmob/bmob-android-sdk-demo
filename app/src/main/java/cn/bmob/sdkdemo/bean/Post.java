package cn.bmob.sdkdemo.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * @author zhangchaozhou
 */
public class Post extends BmobObject {

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 发布者
     */
    private User author;
    /**
     * 图片
     */
    private BmobFile image;

    /**
     * 一对多关系：用于存储喜欢该帖子的所有用户
     */
    private BmobRelation likes;


    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Post setAuthor(User author) {
        this.author = author;
        return this;
    }

    public BmobFile getImage() {
        return image;
    }

    public Post setImage(BmobFile image) {
        this.image = image;
        return this;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public Post setLikes(BmobRelation likes) {
        this.likes = likes;
        return this;
    }
}
