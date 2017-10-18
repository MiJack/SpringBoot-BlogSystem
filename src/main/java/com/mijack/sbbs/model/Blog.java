package com.mijack.sbbs.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Blog {

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50) // 映射为字段，值不能为空
    private String title;


    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;
    private boolean draft;
    /**
     * 对应markdown的url地址
     */
    @Column(nullable = false)
    private String contentUrl;

    @Column(name = "create_time", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
//    @Generated(GenerationTime.INSERT)
    private Timestamp createTime;
    @Column(name = "update_time", nullable = false)
    @UpdateTimestamp
    private Timestamp updateTime;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_tag",
            joinColumns = @JoinColumn(name = "blogId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tagId", referencedColumnName = "id"))
    private Set<Tag> tags;
    private int hotValue = 0;
    private String mongoFileId;
    private String mongoFilePath;

    public Blog() {
    }

    public Blog(String title, Category category, String contentUrl, User user) {
        this.title = title;
        this.category = category;
        this.contentUrl = contentUrl;
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blog blog = (Blog) o;

        if (draft != blog.draft) return false;
        if (id != null ? !id.equals(blog.id) : blog.id != null) return false;
        if (title != null ? !title.equals(blog.title) : blog.title != null) return false;
        if (category != null ? !category.equals(blog.category) : blog.category != null) return false;
        if (contentUrl != null ? !contentUrl.equals(blog.contentUrl) : blog.contentUrl != null) return false;
        if (createTime != null ? !createTime.equals(blog.createTime) : blog.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(blog.updateTime) : blog.updateTime != null) return false;
        return user != null ? user.equals(blog.user) : blog.user == null;
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
                '}';
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
}