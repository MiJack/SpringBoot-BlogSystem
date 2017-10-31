package com.mijack.sbbs.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Set;

/**
 * todo 待添加消息队列后将对象补全
 *
 * @author Mr.Yuan
 * @since 2017/10/25
 */
@Document(indexName = "blog", type = "blog", shards = 1)
public class ESBlog {

    @Id  // 主键
    @Field(index = FieldIndex.not_analyzed)
    private Long id; // Blog 实体的 id
    private String title;
    private String content;
    private String summary;
    @Field(index = FieldIndex.not_analyzed)  // 不做全文检索字段
    private User user;
    private Set<Tag> tags;  // 标签
    private Category category; // 分类
    @Field(index = FieldIndex.not_analyzed)  // 不做全文检索字段
    private Timestamp createTime;
    @Field(index = FieldIndex.not_analyzed)  // 不做全文检索字段
    private Timestamp updateTime;

    public ESBlog() {
    }

    public ESBlog(Long id, String title, String content, String summary,User user, Category category, Set<Tag> tags, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.user = user;
        this.tags = tags;
        this.category = category;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

