package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.MongoGridFile;
import com.mijack.sbbs.service.MongoFileService;
import com.mijack.sbbs.utils.Utils;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
@Service
public class MongoFileServiceImpl implements MongoFileService {
    @Autowired
    GridFsTemplate gridFsTemplate;

    @Override
    public MongoGridFile saveMongoGridFile(MongoGridFile mongoGridFile) {
        GridFSFile gridFSFile = gridFsTemplate.store(mongoGridFile.getInputStream(), mongoGridFile.getPath()
                , mongoGridFile.getDBObject());
        mongoGridFile.setRawFile(gridFSFile);
        return mongoGridFile;
    }

    @Override
    public MongoGridFile updateMongoFile(String mongoFileId, MongoGridFile newMongoFile) {
        if (Utils.isEmpty(mongoFileId)) {
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(mongoFileId)));
        }
        return saveMongoGridFile(newMongoFile);
    }
}
