package com.demo.project.common.persistence.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.project.common.persistence.service.ProjectService;
import com.demo.project.common.persistence.template.modal.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("getProject/{pid}")
    public Project getProject(@PathVariable(value = "pid") Integer pid) {
        return projectService.selectOne(new EntityWrapper<Project>().eq("project_id", pid));
    }

    @RequestMapping("hi")
    public String sayHi() {
        return "hi";
    }
}

