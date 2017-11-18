package com.mijack.sbbs.service;

import com.mijack.sbbs.model.MongoGridFile;
import com.mijack.sbbs.model.StorageObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.InputStream;
import java.util.List;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
public interface StorageService {

    StorageObject saveStorageObject(StorageObject storageObject, InputStream inputStream);

    StorageObject removeStorageObject(Query query);

    StorageObject findStorageObject(Query query);

    List<StorageObject> findStorageObjects(Query query);


    static Criteria resourcePathCriteria(String requestURI) {
        return Criteria.where("resourcePath").is(requestURI);
    }

    static Criteria resourceCriteria(@PathVariable("type") String type, @PathVariable("uuid") String uuid, @PathVariable("extensionName") String extensionName) {
        return Criteria.where("type").is(type).and("uuid").is(uuid).and("extensionName").is(extensionName);
    }

}
