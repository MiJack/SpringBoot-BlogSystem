package com.mijack.sbbs.service;

import com.mijack.sbbs.model.MongoGridFile;
import com.mijack.sbbs.model.StorageObject;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
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

    StorageObject removeStorageObject(Bson query);

    StorageObject findStorageObject(Bson query);

    List<StorageObject> findStorageObjectList(Bson query);

    @Deprecated
    static Criteria resourcePathCriteria(String requestURI) {
        return Criteria.where("resourcePath").is(requestURI);
    }

    @Deprecated
    static Criteria resourceCriteria(String type, String uuid, String extensionName) {
        return Criteria.where("type").is(type).and("uuid").is(uuid).and("extensionName").is(extensionName);
    }

    static Bson resourcePathFilters(String requestURI) {
        return Filters.eq("resourcePath", requestURI);
    }

    static Bson resourceFilters(String type, String uuid, String extensionName) {
        Bson typeFilter = Filters.eq("type", type);
        Bson uuidFilter = Filters.eq("uuid", uuid);
        Bson extensionNameFilter = Filters.eq("extensionName", extensionName);
        return Filters.and(typeFilter, uuidFilter, extensionNameFilter);
    }

}
