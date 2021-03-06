package com.mijack.sbbs.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Mr.Yuan
 */
@Entity
public class Blog {

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50) // 映射为字段，值不能为空
    private String title;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category")
    private Category category;
    private boolean draft;
    /**
     * 对应markdown的url地址
     */
    @Column(nullable = false)
    private String contentUrl;

    @Column(name = "create_time", nullable = false)
    @CreationTimestamp
    private Timestamp createTime;
    @Column(name = "update_time", nullable = false)
    @UpdateTimestamp
    private Timestamp updateTime;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_tag",
            joinColumns = @JoinColumn(name = "blogId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tagId", referencedColumnName = "id"))
    private Set<Tag> tags;
    private String summary;
    private int hotValue = 0;
    private String mongoFileId;
    private String mongoFilePath;

    public Blog() {
    }

    public Blog(String title, Category category, String contentUrl, User user, String summary) {
        this.title = title;
        this.category = category;
        this.contentUrl = contentUrl;
        this.user = user;
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }


    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public int getHotValue() {
        return hotValue;
    }

    public void setHotValue(int hotValue) {
        this.hotValue = hotValue;
    }

    public void setMongoFileId(String mongoFileId) {
        this.mongoFileId = mongoFileId;
    }

    public String getMongoFileId() {
        return mongoFileId;
    }

    public void setMongoFilePath(String mongoFilePath) {
        this.mongoFilePath = mongoFilePath;
    }

    public String getMongoFilePath() {
        return mongoFilePath;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blog blog = (Blog) o;

        if (draft != blog.draft) return false;
        if (hotValue != blog.hotValue) return false;
        if (id != null ? !id.equals(blog.id) : blog.id != null) return false;
        if (title != null ? !title.equals(blog.title) : blog.title != null) return false;
        if (category != null ? !category.equals(blog.category) : blog.category != null) return false;
        if (contentUrl != null ? !contentUrl.equals(blog.contentUrl) : blog.contentUrl != null) return false;
        if (createTime != null ? !createTime.equals(blog.createTime) : blog.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(blog.updateTime) : blog.updateTime != null) return false;
        if (user != null ? !user.equals(blog.user) : blog.user != null) return false;
        if (tags != null ? !tags.equals(blog.tags) : blog.tags != null) return false;
        if (mongoFileId != null ? !mongoFileId.equals(blog.mongoFileId) : blog.mongoFileId != null) return false;
        return mongoFilePath != null ? mongoFilePath.equals(blog.mongoFilePath) : blog.mongoFilePath == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (draft ? 1 : 0);
        result = 31 * result + (contentUrl != null ? contentUrl.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + hotValue;
        result = 31 * result + (mongoFileId != null ? mongoFileId.hashCode() : 0);
        result = 31 * result + (mongoFilePath != null ? mongoFilePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", draft=" + draft +
                ", contentUrl='" + contentUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", user=" + user +
                ", tags=" + tags +
                ", hotValue=" + hotValue +
                ", mongoFileId='" + mongoFileId + '\'' +
                ", mongoFilePath='" + mongoFilePath + '\'' +
                '}';
    }
}