package com.mijack.sbbs.service;

import com.mijack.sbbs.model.MongoGridFile;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
public interface MongoFileService {

    MongoGridFile saveMongoGridFile(MongoGridFile mongoGridFile);

    MongoGridFile updateMongoFile(String mongoFileId, MongoGridFile mongoFile);

    MongoGridFile findMongoFile(String requestURI);
}
