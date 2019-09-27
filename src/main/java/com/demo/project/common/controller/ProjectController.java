package com.demo.project.common.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.modal.Group;
import com.demo.project.common.persistence.modal.Organization;
import com.demo.project.common.persistence.service.GroupService;
import com.demo.project.common.persistence.service.OrganizationService;
import com.demo.project.common.persistence.service.ProjectService;
import com.demo.project.common.persistence.modal.Project;
import com.demo.project.common.persistence.modal.WxUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private GroupService groupService;

    @Autowired
    private OrganizationService organizationService;


    @Value("${web.upload-path}")
    private String path;

    @RequestMapping(value = "getProject/{pid}", method = RequestMethod.GET)
    public Object getProject(@PathVariable(value = "pid") Integer pid,
                             HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser == null) {
            map.put("code",1);
            map.put("msg","登录状态失效");
            return map;
        }else if (wxUser.getStatus() == 0) {
            map.put("code",3);
            map.put("msg","您的账号未激活");
            return map;
        }else if (groupService.selectOne(new EntityWrapper<Group>().eq("project_id",pid)
                .eq("user_id",wxUser.getUserId())) != null
                    || (projectService.selectById(pid).getLeaderId() == wxUser.getUserId())) {
            map.put("code",0);
            map.put("project",projectService.getProject(Integer.valueOf(pid)));
            return map;
        }else {
            map.put("code",2);
            map.put("msg","暂无权限");
            return map;
        }

    }

    @ApiOperation("按条件获取所有项目")
    @RequestMapping(value = "getAllProjects", method = RequestMethod.GET )
    public HashMap<String, Object> getProject(@RequestParam(value = "pageNumber", required = false) Integer page,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "searchText", required = false) String keyword,
                                                    HttpServletRequest request
                             ) {
        HashMap<String,Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser == null) {
            map.put("code",1);
            map.put("msg","登录状态失效");
            return map;
        }else if (wxUser.getStatus() == 0) {
            map.put("code",3);
            map.put("msg","您的账号未激活");
            return map;
        }
        if(page == null )
            page = 1;
        if (pageSize == null)
            pageSize =10;
        if (keyword == null)
            keyword = "";
        Page<HashMap<String, Object>> pager = projectService.getProjects(new Page<>(page,pageSize), keyword, wxUser.getUserId());
        map.put("records",pager.getRecords());
        map.put("pages",pager.getPages());
        map.put("total",pager.getTotal());
        map.put("code",0);
        return map;
    }

    @ApiOperation("获取所有项目id和名字")
    @RequestMapping(value = "getProjectNames", method = RequestMethod.GET )
    public List<HashMap<String,Object>> getProjectNames(HttpServletRequest request) {
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        List<Map<String, Object>> projectIds = groupService.selectMaps(new EntityWrapper<Group>().setSqlSelect("project_id").eq("user_id",wxUser.getUserId()));
        List<Integer> ids = new ArrayList<>();
        for(Map<String, Object> project : projectIds) {
            if(!ids.contains(project.get("project_id")))
                ids.add((Integer)project.get("project_id"));
        }
        return projectService.getProjectNames(ids, wxUser.getUserId());
    }

    @ApiOperation("检查项目名字是否合法")
    @RequestMapping(value = "checkProjectName", method = RequestMethod.GET )
    public boolean checkProjectNames(@RequestParam(value = "projectName", required = false) String projectName) {
        Project project = projectService.selectOne(new EntityWrapper<Project>().eq("project_name",projectName));
        if(project != null)
            return false;
        else
            return true;
    }

    @ApiOperation("发布项目")
    @RequestMapping(value = "addProject", method = RequestMethod.POST )
    public Object addProject(@RequestParam(value = "img", required = false) String pics,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam("projectName") String projectName,
                         @RequestParam(value = "userList", required = false) List<Integer> userList,
                         HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效");
        }else if (wxUser.getAuthority() < 2) {
            map.put("code",2);
            map.put("msg", "权限不足");
        }else if (wxUser.getStatus() == 0) {
            map.put("code",3);
            map.put("msg","您的账号未激活");
            return map;
        }else {
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
            project.setCreateTime(new Date());
            project.setLastUpdTime(new Date());
            projectService.insert(project);
            if (userList!=null) {
                groupService.addStaffs(project.getProjectId(), userList);
            }
            map.put("projectId", project.getProjectId());
            map.put("code", 0);
        }
        return map;
    }


}

