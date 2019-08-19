package com.demo.project.common.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.service.ProjectService;
import com.demo.project.common.persistence.template.modal.Project;
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
    public Project getProject(@PathVariable(value = "pid") Integer pid) {
        return projectService.selectOne(new EntityWrapper<Project>().eq("project_id", pid));
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

    @RequestMapping(value="/testuploadimg", method = RequestMethod.POST)
    public @ResponseBody String uploadImg(@RequestParam("file") MultipartFile file,
                                          HttpServletRequest request) {
        String contentType = file.getContentType();
//        String fileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".png";
        /*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*/
//        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        try {
            FileUtil.uploadFile(file.getBytes(), path, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //返回json
        return "uploadimg success : "+path+fileName;
    }

}

