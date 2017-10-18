package com.mijack.sbbs.model;

import com.mijack.sbbs.vo.MediaType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;

import java.io.InputStream;

/**
 * @author Mr.Yuan
 * @since 2017/10/18
 */
public class MongoGridFile {
    private InputStream inputStream;
    private String path;
    private String originFile;
    private long uploaderId;
    private MediaType mediaType;
    private GridFSFile rawFile;

    public MongoGridFile(InputStream inputStream,
                         String path, String originFile,
                         long uploaderId, MediaType mediaType) {

        this.inputStream = inputStream;
        this.path = path;
        this.originFile = originFile;
        this.uploaderId = uploaderId;
        this.mediaType = mediaType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriginFile() {
        return originFile;
    }

    public void setOriginFile(String originFile) {
        this.originFile = originFile;
    }

    public long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public DBObject getDBObject() {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("originFile", originFile);
        dbObject.put("uploaderId", uploaderId);
        return dbObject;
    }

    public void setRawFile(GridFSFile rawFile) {
        this.rawFile = rawFile;
    }

    public GridFSFile getRawFile() {
        return rawFile;
    }

    public String getId() {
        return rawFile != null ? rawFile.getId().toString() : null;
    }
}
