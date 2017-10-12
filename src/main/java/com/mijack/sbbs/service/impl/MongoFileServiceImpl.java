package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.MongoFile;
import com.mijack.sbbs.repository.MongoFileRepository;
import com.mijack.sbbs.service.MongoFileService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
@Service
public class MongoFileServiceImpl implements MongoFileService {
    @Autowired
    MongoFileRepository mongoFileRepository;

    @Override
    public MongoFile saveMongoFile(MongoFile mongoFile) {
        return mongoFileRepository.save(mongoFile);
    }

    @Override
    public MongoFile updateMongoFile(String mongoFileId, MongoFile newMongoFile) {
        if (mongoFileId == null) {
            return null;
        }
        if (Utils.isEquals(mongoFileId, newMongoFile.getId())) {
            return mongoFileRepository.save(newMongoFile);
        }
        MongoFile mongoFile = mongoFileRepository.findOne(mongoFileId);
        mongoFile.setContent(newMongoFile.getContent());
        mongoFile.setContentType(newMongoFile.getContentType());
//        mongoFile.setId(newMongoFile.getId());
        mongoFile.setMd5(newMongoFile.getMd5());
        mongoFile.setName(newMongoFile.getName());
        mongoFile.setOwner(newMongoFile.getOwner());
        mongoFile.setPath(newMongoFile.getPath());
        mongoFile.setSize(newMongoFile.getSize());
        return mongoFileRepository.save(mongoFile);
    }
}
