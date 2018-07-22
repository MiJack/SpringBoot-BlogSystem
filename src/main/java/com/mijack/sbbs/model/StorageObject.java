package com.mijack.sbbs.model;

import com.mijack.sbbs.vo.FileType;
import com.mijack.sbbs.vo.MediaType;
import com.mongodb.gridfs.GridFSFile;
import org.bson.types.ObjectId;

/**
 * @author Mr.Yuan
 * @since 2017/10/19
 */
public class StorageObject {
    private String resourcePath;
    private String originFileName;
    private User uploader;
    private FileType fileType;
    private MediaType mediaType;
    private String storageId;
    private ObjectId rawFile;

    public StorageObject(String resourcePath, String originFileName, FileType fileType,User uploader, MediaType mediaType) {
        this.resourcePath = resourcePath;
        this.originFileName = originFileName;
        this.uploader = uploader;
        this.mediaType = mediaType;
        this.fileType = fileType;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public ObjectId getRawFile() {
        return rawFile;
    }

    public void setRawFile(ObjectId rawFile) {
        this.rawFile = rawFile;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
