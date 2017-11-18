package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.StorageObject;
import com.mijack.sbbs.service.StorageService;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.vo.FileType;
import com.mijack.sbbs.vo.MediaType;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.mijack.sbbs.service.StorageService.resourcePathCriteria;
/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    UserService userService;

    @Override
    public StorageObject saveStorageObject(StorageObject storageObject, InputStream content) {
        GridFSFile file = gridFsTemplate.store(content, storageObject.getOriginFileName());
        file.put("uploader", storageObject.getUploader().getId());
        file.put("resourcePath", storageObject.getResourcePath());
        file.put("originFileName", storageObject.getOriginFileName());
        file.put("fileType", storageObject.getFileType().name());
        file.put("mediaType", storageObject.getMediaType().getContentType());
        file.save();
        storageObject.setRawFile(file);
        storageObject.setStorageId(file.getId().toString());
        return storageObject;
    }

    @Override
    public StorageObject removeStorageObject(Query query) {
        StorageObject storageObject = findStorageObject(query);
        gridFsTemplate.delete(query);
        return storageObject;
    }

    @Override
    public StorageObject findStorageObject(Query query) {
        GridFSDBFile file = gridFsTemplate.findOne(query);
        StorageObject storageObject = create(file);
        return storageObject;
    }

    public StorageObject create(GridFSDBFile file) {
        if (file == null) {
            return null;
        }
        StorageObject storageObject = new StorageObject(
                file.get("resourcePath").toString(),
                file.get("originFileName").toString(),
                FileType.valueOf(file.get("fileType").toString()),
                userService.findUser(Long.valueOf(file.get("uploader").toString())),
                MediaType.from(file.get("mediaType").toString()));
        storageObject.setRawFile(file);
        storageObject.setStorageId(file.getId().toString());
        return storageObject;
    }

    @Override
    public List<StorageObject> findStorageObjects(Query query) {
        return gridFsTemplate.find(query)
                .stream().map(gridFSDBFile -> create(gridFSDBFile))
                .collect(Collectors.toList());
    }
}
