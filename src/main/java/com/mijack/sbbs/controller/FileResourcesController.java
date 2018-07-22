package com.mijack.sbbs.controller;

import static com.mijack.sbbs.service.StorageService.*;

import com.mijack.sbbs.model.StorageObject;
import com.mijack.sbbs.service.StorageService;
import com.mijack.sbbs.utils.Utils;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Mr.Yuan
 * @since 2017/10/18
 */
@Controller
public class FileResourcesController {
    @Autowired
    StorageService storageService;
    @Autowired
    GridFSBucket gridFSBucket;

    @GetMapping("/resource/{type}/{uuid}.{extensionName}")
    public void fileView(@PathVariable("type") String type,
                         @PathVariable("uuid") String uuid,
                         @PathVariable("extensionName") String extensionName,
                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        String requestURI = request.getRequestURI();
        StorageObject storageObject = storageService.findStorageObject(
                Filters.or(resourcePathFilters(requestURI), resourceFilters(type, uuid, extensionName))
        );
        if (storageObject != null) {
            ObjectId rawFile = storageObject.getRawFile();
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=" + storageObject.getOriginFileName());
            response.addHeader(HttpHeaders.CONTENT_TYPE, storageObject.getMediaType().getContentType());
            response.setStatus(HttpServletResponse.SC_OK);
            gridFSBucket.downloadToStream(rawFile, outputStream);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            outputStream.write("File was not fount".getBytes());
        }
        outputStream.flush();
        outputStream.close();
    }

    @GetMapping("/repo/resource/{type}/{uuid}.{extensionName}")
    public void fileDownload(
            @PathVariable("type") String type,
            @PathVariable("uuid") String uuid,
            @PathVariable("extensionName") String extensionName,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();

        String requestURI = request.getRequestURI();
        StorageObject storageObject = storageService.findStorageObject(
                Filters.or(resourcePathFilters(requestURI), resourceFilters(type, uuid, extensionName)));
        if (storageObject != null) {
            ObjectId rawFile = storageObject.getRawFile();
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
                    ContentDisposition
                            .builder("form-data")
                            .name("attachment")
                            .filename(Utils.urlEncode(storageObject.getOriginFileName()))
                            .build().toString());
            response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString());
            response.setStatus(HttpServletResponse.SC_OK);
            gridFSBucket.downloadToStream(rawFile, outputStream);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            outputStream.write("File was not fount".getBytes());
        }
        outputStream.flush();
        outputStream.close();
    }

}
