package com.mijack.sbbs.service.impl;

import java.io.InputStream;
import java.util.List;

import com.mijack.sbbs.model.StorageObject;
import com.mijack.sbbs.service.StorageService;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.Utils;
import com.mijack.sbbs.vo.FileType;
import com.mijack.sbbs.vo.MediaType;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    UserService userService;

    @Override
    public StorageObject saveStorageObject(StorageObject storageObject, InputStream content) {
        // Get the input stream
        Document document = new Document("type", "presentation")
                .append("uploader", storageObject.getUploader().getId())
                .append("resourcePath", storageObject.getResourcePath())
                .append("originFileName", storageObject.getOriginFileName())
                .append("fileType", storageObject.getFileType().name())
                .append("mediaType", storageObject.getMediaType().getContentType());
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1024)
                .metadata(document);

        ObjectId fileId = gridFSBucket.uploadFromStream(storageObject.getOriginFileName(), content, options);
        storageObject.setStorageId(fileId.toHexString());
        storageObject.setRawFile(fileId);
        return storageObject;
    }

    @Override
    public StorageObject removeStorageObject(Bson query) {
        StorageObject storageObject = findStorageObject(query);
        gridFSBucket.delete(storageObject.getRawFile());
        return storageObject;
    }

    @Override
    public StorageObject findStorageObject(Bson query) {
        List<StorageObject> list = findStorageObjectList(query);
        if (Utils.size(list) > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<StorageObject> findStorageObjectList(Bson query) {
        MongoCursor<GridFSFile> iterator = gridFSBucket.find(query).iterator();
        List<StorageObject> list = Lists.newArrayList();
        if (iterator.hasNext()) {
            GridFSFile file = iterator.next();
            StorageObject storageObject = create(file);
            list.add(storageObject);
        }
        return list;
    }

    public StorageObject create(GridFSFile file) {
        if (file == null) {
            return null;
        }
        ObjectId objectId = file.getObjectId();
        Document metadata = file.getMetadata();
        StorageObject storageObject = new StorageObject(
                metadata.get("resourcePath").toString(),
                metadata.get("originFileName").toString(),
                FileType.valueOf(metadata.get("fileType").toString()),
                userService.findUser(Long.valueOf(metadata.get("uploader").toString())),
                MediaType.from(metadata.get("mediaType").toString()));
        storageObject.setRawFile(objectId);
        storageObject.setStorageId(file.getId().toString());
        return storageObject;
    }

}
