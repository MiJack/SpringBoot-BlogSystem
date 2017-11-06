package com.mijack.sbbs.controller;

import com.mijack.sbbs.model.StorageObject;
import com.mijack.sbbs.service.StorageService;
import com.mijack.sbbs.utils.Utils;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author Mr.Yuan
 * @since 2017/10/18
 */
@Controller
public class FileResourcesController {
    @Autowired
    StorageService storageService;

    @GetMapping("/resource/{type}/{uuid}.{extensionName}")
    public ResponseEntity<InputStreamResource> fileView(@PathVariable("type") String type,
                                                        @PathVariable("uuid") String uuid,
                                                        @PathVariable("extensionName") String extensionName,
                                                        HttpServletRequest httpRequest) throws IOException {
        String requestURI = httpRequest.getRequestURI();
        StorageObject storageObject = storageService.findStorageObject(
                new Query(
                        new Criteria().orOperator(
                                Criteria.where("resourcePath").is(requestURI),
                                Criteria.where("type").is(type).and("uuid").is(uuid).and("extensionName").is(extensionName)
                        )
                )
        );
        if (storageObject != null) {
            GridFSDBFile rawFile = (GridFSDBFile) storageObject.getRawFile();
            InputStreamResource inputStreamResource = new InputStreamResource(rawFile.getInputStream());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "filename=" + storageObject.getOriginFileName());
            headers.add(HttpHeaders.CONTENT_TYPE, storageObject.getMediaType().getContentType());
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<InputStreamResource> entity = new ResponseEntity<>(inputStreamResource, headers, statusCode);
            return entity;
        } else {
            return new ResponseEntity("File was not fount", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/repo/resource/{type}/{uuid}.{extensionName}")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable("type") String type,
                                                            @PathVariable("uuid") String uuid,
                                                            @PathVariable("extensionName") String extensionName,
                                                            HttpServletRequest httpRequest) throws IOException {
        String requestURI = httpRequest.getRequestURI();
        StorageObject storageObject = storageService.findStorageObject(
                new Query(
                        new Criteria().orOperator(
                                Criteria.where("resourcePath").is(requestURI),
                                Criteria.where("type").is(type).and("uuid").is(uuid).and("extensionName").is(extensionName)
                        )
                )
        );
        if (storageObject != null) {
            GridFSDBFile rawFile = (GridFSDBFile) storageObject.getRawFile();
            InputStreamResource inputStreamResource = new InputStreamResource(rawFile.getInputStream());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", Utils.urlEncode(storageObject.getOriginFileName()));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<InputStreamResource> entity = new ResponseEntity<>(inputStreamResource, headers, statusCode);
            return entity;
        } else {
            return new ResponseEntity("File was not fount", HttpStatus.NOT_FOUND);
        }
    }

}
