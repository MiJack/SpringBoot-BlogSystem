package com.mijack.sbbs.model;

import java.util.Map;

/**
 * @author Mr.Yuan
 * @since 2017/11/6
 */
public class BlogStatistics {
    private int blogCount;
    private Map<Category, Long> categoryStatistics;
    private Map<Tag, Long> tagStatistics;
    private User user;

    public int getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(int blogCount) {
        this.blogCount = blogCount;
    }

    public Map<Category, Long> getCategoryStatistics() {
        return categoryStatistics;
    }

    public void setCategoryStatistics(Map<Category, Long> categoryStatistics) {
        this.categoryStatistics = categoryStatistics;
    }

    public Map<Tag, Long> getTagStatistics() {
        return tagStatistics;
    }

    public void setTagStatistics(Map<Tag, Long> tagStatistics) {
        this.tagStatistics = tagStatistics;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogStatistics that = (BlogStatistics) o;

        if (blogCount != that.blogCount) return false;
        if (categoryStatistics != null ? !categoryStatistics.equals(that.categoryStatistics) : that.categoryStatistics != null)
            return false;
        if (tagStatistics != null ? !tagStatistics.equals(that.tagStatistics) : that.tagStatistics != null)
            return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = blogCount;
        result = 31 * result + (categoryStatistics != null ? categoryStatistics.hashCode() : 0);
        result = 31 * result + (tagStatistics != null ? tagStatistics.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BlogStatistics{" +
                "blogCount=" + blogCount +
                ", categoryStatistics=" + categoryStatistics +
                ", tagStatistics=" + tagStatistics +
                ", user=" + user +
                '}';
    }
}
