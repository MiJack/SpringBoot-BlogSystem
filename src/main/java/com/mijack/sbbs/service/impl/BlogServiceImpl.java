package com.mijack.sbbs.service.impl;

import com.google.common.collect.Sets;
import com.mijack.sbbs.model.*;
import com.mijack.sbbs.repository.BlogRepository;
import com.mijack.sbbs.repository.EsBlogRepository;
import com.mijack.sbbs.service.BlogService;
import com.mijack.sbbs.service.StorageService;
import com.mijack.sbbs.utils.AssertUtils;
import com.mijack.sbbs.utils.Utils;
import com.mijack.sbbs.vo.FileType;
import com.mijack.sbbs.vo.MediaType;
import com.mongodb.gridfs.GridFSDBFile;
import okio.ByteString;
import okio.Okio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * @author Mr.Yuan
 * @since 2017/10/9
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    StorageService storageService;
    @Autowired
    EsBlogRepository esBlogRepository;

    @Override
    public Page<Blog> listBlog(User user, Pageable pageable) {
        AssertUtils.notNull(user, UsernameNotFoundException.class, "用户未找到");
        return blogRepository.findAllByUser(user, pageable);
    }

    @Override
    public Page<Blog> listBlog(Tag tag, Pageable pageable) {
        return blogRepository.findAllByTagsContainingAndDraft(tag, false, pageable);
    }

    @Override
    public Page<Blog> listBlog(Category category, Pageable pageable) {
        return blogRepository.findAllByCategoryAndDraft(category, false, pageable);
    }

    @Override
    public boolean deleteBlog(Blog blog) {
        // 移除MongoDB和elasticsearch
        blogRepository.delete(blog);
        storageService.removeStorageObject(blogQuery(blog));
        esBlogRepository.delete(blog.getId());
        return true;
    }

    @Override
    public Blog findBlog(long blogId) {
        return blogRepository.findOne(blogId);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        // 更新数据库
        blog = blogRepository.save(blog);
        // 更新elasticsearch
        String blogContent = getBlogContent(blog);
        String summary = blog.getSummary();
        if (Utils.isEmpty(summary)) {
            summary = Utils.markdownSummary(blogContent);
            blog.setSummary(summary);
            blog = blogRepository.save(blog);
        }
        if (!blog.isDraft()) {
            // 草稿不需要保存到elastic search数据库
            ESBlog esBlog = new ESBlog(blog.getId(),
                    blog.getTitle(),
                    blogContent,
                    Utils.isEmpty(summary) ? Utils.markdownSummary(blogContent) : summary,
                    blog.getUser(),
                    blog.getCategory(), blog.getTags(), blog.getCreateTime(), blog.getUpdateTime());
            esBlogRepository.save(esBlog);
        }
        return blog;
    }

    public String getBlogContent(Blog blog) {
        StorageObject storageObject = getBlogStorageObject(blog);
        if (storageObject == null || !(storageObject.getRawFile() instanceof GridFSDBFile)) {
            return null;
        }
        GridFSDBFile rawFile = (GridFSDBFile) storageObject.getRawFile();
        try {
            ByteString byteString = Okio.buffer(
                    Okio.source(rawFile.getInputStream())).readByteString();
            return byteString.utf8();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private StorageObject getBlogStorageObject(Blog blog) {
        return storageService.findStorageObject(blogQuery(blog));
    }

    private Query blogQuery(Blog blog) {
        return new Query(Criteria.where("resourcePath").is(blog.getContentUrl()));
    }

    @Override
    public Blog createBlog(User user, String blogTitle, String blogMarkdown, Category category, Set<Tag> tags, boolean isDraft) {
        String blogName = blogTitle + MediaType.markdown.getExtensionName();
        String blogPath = "/resource/blog/" + UUID.randomUUID().toString()
                + MediaType.markdown.getExtensionName();
        StorageObject mongoGridFile = new StorageObject(blogPath, blogName, FileType.blog, user, MediaType.markdown);
        mongoGridFile = storageService.saveStorageObject(mongoGridFile, Utils.inputStream(blogMarkdown));
        Blog blog = new Blog(blogTitle, category, mongoGridFile.getResourcePath(), user, Utils.markdownSummary(blogMarkdown));
        blog.setDraft(isDraft);
        blog.setMongoFileId(mongoGridFile.getStorageId());
        blog.setMongoFilePath(mongoGridFile.getResourcePath());
        blog.setTags(tags);
        return saveBlog(blog);
    }

    @Override
    public Blog updateBlog(Blog blog, User user, String blogTitle, String blogMarkdown, Category category, Set<Tag> tags, boolean isDraft) {
        blog.setTags(tags);
        blog.setTitle(blogTitle);
        blog.setCategory(category);

        String blogName = blogTitle + MediaType.markdown.getExtensionName();
        StorageObject mongoGridFile = new StorageObject(
                blog.getMongoFilePath(), blogName, FileType.blog, user, MediaType.markdown);
        storageService.removeStorageObject(new Query(Criteria.where("_id").is(blog.getMongoFileId())));
        mongoGridFile = storageService.saveStorageObject(mongoGridFile, Utils.inputStream(blogMarkdown));
        blog.setDraft(isDraft);
        blog.setSummary(Utils.markdownSummary(blogMarkdown));
        blog.setMongoFileId(mongoGridFile.getStorageId());
        blog.setMongoFilePath(mongoGridFile.getResourcePath());
        blog.setTags(Sets.newHashSet(tags.iterator()));
        return saveBlog(blog);
    }

    @Override
    public void deleteAllBlog() {
        blogRepository.findAll().forEach(blog -> {
            blogRepository.delete(blog);
            storageService.removeStorageObject(blogQuery(blog));
            esBlogRepository.delete(blog.getId());
        });
    }
}
