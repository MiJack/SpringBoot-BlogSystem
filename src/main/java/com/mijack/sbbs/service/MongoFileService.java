package com.mijack.sbbs.service;

import com.mijack.sbbs.model.MongoFile;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
public interface MongoFileService {

    MongoFile saveMongoFile(MongoFile mongoFile);

    MongoFile updateMongoFile(String mongoFileId, MongoFile mongoFile);
}
