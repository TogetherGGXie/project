package com.demo.project.common.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.service.ProjectService;
import com.demo.project.common.persistence.template.modal.Project;
import com.demo.project.common.persistence.template.modal.ProjectLog;
import com.demo.project.common.persistence.template.modal.WxUser;
import com.demo.project.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
@Api("项目管理")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Value("${web.upload-path}")
    private String path;

    @RequestMapping(value = "getProject/{pid}", method = RequestMethod.GET)
    public Object getProject(@PathVariable(value = "pid") Integer pid) {
        return projectService.getProject(Integer.valueOf(pid));
    }

    @ApiOperation("按条件获取所有项目")
    @RequestMapping(value = "getAllProjects", method = RequestMethod.GET )
    public Page<HashMap<String, Object>> getProject(@RequestParam(value = "pageNumber", required = false) Integer page,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "searchText", required = false) String keyword
                             ) {
        if(page == null )
            page = 1;
        if (pageSize == null)
            pageSize =10;
        if (keyword == null)
            keyword = "";
        Page<HashMap<String, Object>> pager = projectService.getProjects(new Page<>(page,pageSize),keyword);
        return pager;
    }

    @ApiOperation("获取所有项目id和名字")
    @RequestMapping(value = "getProjectNames", method = RequestMethod.GET )
    public List<HashMap<String,Object>> getProjectNames() {

        return projectService.getProjectNames();
    }

    @ApiOperation("检查项目名字是否合法")
    @RequestMapping(value = "checkProjectName", method = RequestMethod.GET )
    public boolean checkProjectNames(@RequestParam(value = "projectName", required = false) String projectName) {
        Project project = projectService.selectOne(new EntityWrapper<Project>().eq("project_name",projectName));
        if(project!=null)
            return false;
        else
            return true;
    }

    @ApiOperation("发布项目")
    @RequestMapping(value = "addProject", method = RequestMethod.POST )
    public Object addProject(@RequestParam("img") String pics,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam("projectName") String projectName,
                         HttpServletRequest request) {
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser == null) {
            return "登录状态失效，请重新打开";
        }
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try{
            if (startTime != null && startTime!= "")
                start = dateFormat.parse(startTime);
            if (endTime != null && endTime!= "")
                end = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Project project = new Project();
        project.setProjectName(projectName);
        project.setLeaderId(wxUser.getUserId());
        project.setStartTime(start);
        project.setEndTime(end);
        project.setImg(pics);
        projectService.insert(project);
        return project.getProjectId();
    }

//    @RequestMapping(value="/testuploadimg", method = RequestMethod.POST)
//    public @ResponseBody String uploadImg(@RequestParam("file") MultipartFile file,
//                                          HttpServletRequest request) {
//        String contentType = file.getContentType();
////        String fileName = file.getOriginalFilename();
//        String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".png";
//        /*System.out.println("fileName-->" + fileName);
//        System.out.println("getContentType-->" + contentType);*/
////        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
//        try {
//            FileUtil.uploadFile(file.getBytes(), path, fileName);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        //返回json
//        return "uploadimg success : "+path+fileName;
//    }

}

