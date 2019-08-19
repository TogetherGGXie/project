package com.demo.project.common.controller;


import com.demo.project.common.persistence.service.ProjectLogService;
import com.demo.project.common.persistence.template.modal.Project;
import com.demo.project.common.persistence.template.modal.ProjectLog;
import com.demo.project.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-12
 */
@Api(value = "日志管理")
@Controller
@RequestMapping("/projectLog")
public class ProjectLogController {
    @Value("${web.upload-path}")
    private String folderPath;

    @Autowired
    private ProjectLogService projectLogService;

    @ApiOperation(value = "添加日志")
    @RequestMapping(value = "/addLog", method = RequestMethod.POST)
    @ResponseBody
    public Object addLog(@RequestParam("pics") String pics,
                                          @RequestParam("date") String date,
                                          @RequestParam("projectId") Integer projectId,
                                          @RequestParam("userId") Integer userId,
                                          @RequestParam("content") String content) {
//        String filePath = folderPath +"/projectLog/";
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date createDate = null;
        try{
            createDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
         }
        ProjectLog projectLog = new ProjectLog();
        projectLog.setContent(content);
        projectLog.setProjectId(projectId);
        projectLog.setUserId(userId);
        projectLog.setPics(pics);
        projectLog.setDate(createDate);
        projectLogService.insert(projectLog);
        return projectLog.getLogId();
    }


}

