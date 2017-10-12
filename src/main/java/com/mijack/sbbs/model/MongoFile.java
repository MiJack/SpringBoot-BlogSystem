package com.mijack.sbbs.model;

import com.mijack.sbbs.vo.MediaType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
@Document
public class MongoFile {
    @Id  // 主键
    private String id;
    private String name; // 文件名称
    private MediaType contentType; // 文件类型
    private long size;
    private String md5;
    private byte[] content; // 文件内容
    private String path; // 文件路径
    private User owner;
    @CreationTimestamp  // 由数据库自动创建时间
    private Timestamp createTime;
    @UpdateTimestamp   // 由数据库自动创建时间
    private Timestamp updateTime;

    public MongoFile() {
    }

    public MongoFile(String name, MediaType contentType, long size, String md5, byte[] content, String path, User owner) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.md5 = md5;
        this.content = content;
        this.path = path;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}