package com.demo.project.common.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.service.ProjectLogService;
import com.demo.project.common.persistence.template.modal.Project;
import com.demo.project.common.persistence.template.modal.ProjectLog;
import com.demo.project.common.persistence.template.modal.WxUser;
import com.demo.project.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
                         @RequestParam("content") String content,
                         HttpServletRequest request) {
        HashMap<String,Object> hashmap = new HashMap<String,Object>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser == null) {
            return "登录状态失效，请重新打开";
        }
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
        projectLog.setUserId(wxUser.getUserId());
        projectLog.setPics(pics);
        projectLog.setDate(createDate);
        projectLogService.insert(projectLog);
        hashmap.put("logId",projectLog.getLogId());
        hashmap.put("projectId",projectLog.getProjectId());
        return hashmap;
    }


    @ApiOperation(value = "获取项目的日志列表")
    @RequestMapping(value = "/getLogs", method = RequestMethod.GET)
    @ResponseBody
    public Page<HashMap<String, Object>> getProject(@RequestParam(value = "pageNumber", required = false) Integer page,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                    @RequestParam(value = "projectId", required = false) Integer projectId
    ) {
        if(page == null )
            page = 1;
        if (pageSize == null)
            pageSize =10;
        if (projectId == null)
            return null;
        Page<HashMap<String, Object>> pager = projectLogService.getLogs(new Page<>(page,pageSize),projectId);
        return pager;
    }
}

