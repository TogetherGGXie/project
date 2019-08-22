package com.demo.project.common.controller;


import com.demo.project.util.FileUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Api(value = "上传图片")
@RestController

public class UploadController {
    @Value("${web.upload-path}")
    private String path;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadImg(@RequestParam("img") MultipartFile pics,
                            @RequestParam("folder") Integer folder) {
        String folderpath ="";
        switch (folder){
            case 1:
                folderpath = "project";
                break;
            case 2:
                folderpath = "projectLog";
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".png";

        fileName = folderpath+"/".concat(fileName);
        System.out.println(fileName);
        try {
            FileUtil.uploadFile(pics.getBytes(), path, fileName);
        } catch (Exception e) {
            return "error";
        }
        return fileName;
    }

}