package com.mijack.sbbs.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author Mr.Yuan
 */
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @OneToOne()
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    private User user;
    @NotNull
    private String token;
    @Column(name = "update_time", nullable = false)
    @UpdateTimestamp
    private Date updateTime;

    public Token() {
    }

    public Token(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public Token(User user, String token, Date updateTime) {
        this.user = user;
        this.token = token;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
