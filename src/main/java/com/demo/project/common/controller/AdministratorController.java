package com.demo.project.common.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.project.common.persistence.modal.*;
import com.demo.project.common.persistence.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-29
 */
@Api("管理员管理")
@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectLogService projectLogService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private KeywordsService keywordsService;

    @Autowired
    private ViewStatisService viewStatisService;


    @ApiOperation("管理员登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object adminLogin(@RequestParam(name = "adminName") String adminName,
                             @RequestParam(name = "password") String password,
                             HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        Administrator administrator = administratorService.selectOne(new EntityWrapper<Administrator>().eq("admin_name", adminName));
        if (administrator == null) {
            map.put("code", 1);
            map.put("msg", "用户名错误");
        } else if(!password.equals(administrator.getPassword())) {
            map.put("code", 2);
            map.put("msg", "密码错误");
        }else {
            map.put("code", 0);
            request.getSession().setAttribute("admin", administrator);
        }
        return map;
    }

    @ApiOperation("管理员获取部门内用户列表")
    @GetMapping("/getUsers")
    public HashMap<String,Object> adminGetUsers(HttpServletRequest request) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            map.put("code", 0);
            map.put("records",wxUserService.getUserList(ids));
        }
        return map;
    }

    @ApiOperation("管理员禁用用户账号")
    @PostMapping("/banUser")
    public HashMap<String,Object> adminBanUsers(@RequestParam(name = "userId") Integer userId,
                                                HttpServletRequest request) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            WxUser wxUser = wxUserService.selectById(userId);
            if (ids.contains(wxUser.getOrganizationId())) {
                wxUser.setStatus(0);
                wxUserService.updateById(wxUser);
                map.put("code", 0);
            } else {
                map.put("code", 1);
                map.put("msg", "您暂无对该用户操作的权限");
            }
        }
        return map;
    }

    @ApiOperation("管理员启用用户账号")
    @PostMapping("/enableUser")
    public HashMap<String,Object> adminEnableUsers(@RequestParam(name = "userId") Integer userId,
                                                HttpServletRequest request) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            WxUser wxUser = wxUserService.selectById(userId);
            if (ids.contains(wxUser.getOrganizationId())) {
                wxUser.setStatus(1);
                wxUserService.updateById(wxUser);
                map.put("code", 0);
            } else {
                map.put("code", 1);
                map.put("msg", "您暂无对该用户操作的权限");
            }
        }
        return map;
    }

    @ApiOperation("管理员获取项目列表")
    @GetMapping("/getProjects")
    public HashMap<String,Object> adminGetProjects(HttpServletRequest request) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            List<HashMap<String,Object>> projectList = projectService.getProjectList(ids);
            List<Integer> projectIds = new ArrayList<>();
            for (HashMap<String,Object>  resultMap : projectList) {
                projectIds.add((Integer)resultMap.get("projectId"));
            }
            List<HashMap<String,Object>> statis = groupService.getStatis(projectIds);
            HashMap<String, List<HashMap<String, Object>>> group = groupService.getUsers(projectIds);
            for (HashMap<String,Object> project : projectList ){
                for (HashMap<String,Object> count : statis) {
                    if (count.get("projectId") == project.get("projectId")) {
                        project.put("count",count.get("count"));
                    }
                }
                project.put("group",group.get(project.get("projectId").toString()));
            }
            map.put("record",projectList);
            map.put("code",0);
        }
        return map;
    }

    @ApiOperation("管理员编辑项目")
    @PostMapping("/editProject")
    public HashMap<String,Object> adminEditProject(HttpServletRequest request,
                                                   @RequestParam(name = "project") Project project) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            Integer oid = projectService.getOrganizationId(project.getProjectId());
            if(ids.contains(oid)){
                projectService.updateById(project);
                map.put("code",0);
            } else {
                map.put("code", 1);
                map.put("msg", "您暂无编辑该项目的权限");
            }
        }
        return map;
    }

    @ApiOperation("管理员获取日志列表")
    @GetMapping("/getProjectLogs")
    public HashMap<String,Object> adminGetProjectLogs(HttpServletRequest request) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            List<HashMap<String,Object>> projectLogList = projectLogService.getProjectLogList(ids);
            List<Integer> projectLogIds = new ArrayList<>();
            for (HashMap<String,Object>  resultMap : projectLogList) {
                projectLogIds.add((Integer)resultMap.get("logId"));
            }
            HashMap<String, List<HashMap<String, Object>>> viewHistory = viewStatisService.getHistory(projectLogIds);
            for (HashMap<String,Object> projectLog : projectLogList ){
                projectLog.put("viewHistory",viewHistory.get(projectLog.get("logId").toString()));
            }
            map.put("record",projectLogList);
            map.put("code",0);
        }
        return map;
    }

    @ApiOperation("管理员编辑日志")
    @PostMapping("/editProjectLog")
    public HashMap<String,Object> adminEditProjectLog(HttpServletRequest request,
                                                   @RequestParam(name = "projectLog") ProjectLog projectLog) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            Integer oid = projectService.getOrganizationId(projectLog.getProjectId());
            if(ids.contains(oid)){
                projectLogService.updateById(projectLog);
                map.put("code",0);
            } else {
                map.put("code", 1);
                map.put("msg", "您暂无编辑该日志的权限");
            }
        }
        return map;
    }

    @ApiOperation("管理员删除日志")
    @PostMapping("/deleteProjectLog")
    public HashMap<String,Object> adminDeleteProjectLog(HttpServletRequest request,
                                                      @RequestParam(name = "logId") Integer logId) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            ProjectLog projectLog = projectLogService.selectById(logId);
            Integer oid = projectService.getOrganizationId(projectLog.getProjectId());
            if(ids.contains(oid)){
                projectLogService.deleteById(logId);
                map.put("code",0);
            } else {
                map.put("code", 1);
                map.put("msg", "您暂无删除该日志的权限");
            }
        }
        return map;
    }

    protected List<Integer> getOrganizationIds(Integer oid){
        List<Map<String, Object>> oids = organizationService.selectMaps(new EntityWrapper<Organization>().setSqlSelect("id,pid").orderBy("id"));
        List<Integer> ids = new ArrayList<>();
        ids.add(oid);
        for(Map<String, Object> organization : oids) {
            if(ids.contains(organization.get("pid")))
                ids.add((Integer)organization.get("id"));
        }
        return ids;
    }

}

