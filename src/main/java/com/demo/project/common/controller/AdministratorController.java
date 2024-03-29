package com.demo.project.common.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.project.common.persistence.modal.*;
import com.demo.project.common.persistence.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    public Object adminLogin(@RequestBody HashMap<String, String> loginForm,
                             HttpServletRequest request) {
        String adminName = loginForm.get("adminName");
        String password = loginForm.get("password");
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

    @ApiOperation("管理员注销登录")
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public HashMap<String, Object> adminLogin(HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("code",1);
        request.getSession().invalidate();
        map.put("code",0);
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
            List<HashMap<String, Object>> records = wxUserService.getUserList(ids);
            map.put("code", 0);
            map.put("records", records);
            map.put("total", records.size());
        }
        return map;
    }

    @ApiOperation("管理员禁用用户账号")
    @PostMapping("/banUser/{userId}")
    public HashMap<String,Object> adminBanUsers(@PathVariable Integer userId,
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
    @PostMapping("/enableUser/{userId}")
    public HashMap<String,Object> adminEnableUsers(@PathVariable Integer userId,
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
            map.put("records",projectList);
            map.put("total",projectList.size());
            map.put("code",0);
        }
        return map;
    }

    @ApiOperation("管理员编辑项目")
    @PostMapping("/editProject")
    public HashMap<String,Object> adminEditProject(HttpServletRequest request,
                                                   @RequestBody HashMap<String, Object> project) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
//            System.out.println("project = " + project.toString());
//            System.out.println(project.get("projectId"));
            List<Integer> ids = getOrganizationIds(administrator.getOid());
//            System.out.println(ids.toString());
            Integer oid = projectService.getOrganizationId((Integer)project.get("projectId"));
//            System.out.println("oid = " + oid);
            Project p = new Project();
            p.setProjectId((Integer)project.get("projectId"));
            p.setProjectName((String)project.get("projectName"));
            p.setIntroduction((String) project.get("introduction"));
            p.setLeaderId((Integer)project.get("leaderId"));
            p.setStartTime(DateUtil.parse((String)project.get("startTime")));
            if (project.get("endTime") != null && project.get("endTime") != "") {
                p.setEndTime(DateUtil.parse((String)project.get("endTime")));
            }
            if(ids.contains(oid)){
                projectService.updateById(p);
                List<Integer> userIds = (List)project.get("group");
                if (userIds != null && !userIds.isEmpty()) {
                    groupService.addStaffs(p.getProjectId(), userIds);
                }
                map.put("code",0);
            } else {
                map.put("code", 1);
                map.put("msg", "您暂无编辑该项目的权限");
            }
        }
        return map;
    }

    @ApiOperation("管理员添加项目")
    @PostMapping("/addProject")
    public HashMap<String,Object> adminAddProject(HttpServletRequest request,
                                                     @RequestBody HashMap<String, Object> project) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
//        System.out.println(project.toString());
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            if (project.get("leaderId") == null) {
                map.put("code",3);
                map.put("msg", "请选择负责人");
            } else {
                List<Integer> ids = getOrganizationIds(administrator.getOid());
                WxUser wxUser = wxUserService.selectById((Integer)project.get("leaderId"));
                if (ids.contains(wxUser.getOrganizationId())) {
                    if (project.get("projectName") != null && project.get("projectName") != "") {
                        Project pro = new Project();
                        pro.setIntroduction((String)project.get("introduction"));
                        if (project.get("endTime") != null && project.get("endTime") != "") {
//                            System.out.println("endTime = " + project.get("endTime"));
                            pro.setEndTime(DateUtil.parse((String)project.get("endTime")));

                        }
//                        System.out.println("startTime = " + project.get("startTime"));
                        pro.setStartTime(DateUtil.parse((String)project.get("startTime")));
                        pro.setProjectName((String)project.get("projectName"));
                        pro.setLeaderId((Integer)project.get("leaderId"));
                        pro.setImg((String)project.get("img"));
                        pro.setCreateTime(DateUtil.date());
                        projectService.insert(pro);
                        List<Integer> userIds = (List)project.get("group");
                        if (userIds != null && !userIds.isEmpty()) {
                            groupService.addStaffs(pro.getProjectId(), userIds);
                        }
                        map.put("code", 0);
                    } else {
                        map.put("code",3);
                        map.put("msg", "请正确填写项目信息");
                    }
                } else {
                    map.put("code", 2);
                    map.put("msg", "您暂无发布该项目的权限");
                }
            }
        }
        return map;
    }

    @ApiOperation("管理员根据项目获取日志列表")
    @GetMapping("/getLogs")
    public HashMap<String,Object> adminGetProjectLog(HttpServletRequest request, @RequestParam(name = "projectId") Integer projectId) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            Project p = projectService.selectById(projectId);
            if (p == null) {
                map.put("code", 1);
                map.put("msg", "项目有误，请稍后重试！");
                return map;
            }
            WxUser leader = wxUserService.selectById(p.getLeaderId());
            if (leader == null) {
                map.put("code", 2);
                map.put("msg", "项目有误，请稍后重试！");
                return map;
            }
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            if (!ids.contains(leader.getOrganizationId())) {
                map.put("code", 1);
                map.put("msg", "您暂无查看该项目的日志的权限！");
                return map;
            }
            List<HashMap<String,Object>> projectLogList = projectLogService.getLogList(projectId);
            List<Integer> projectLogIds = new ArrayList<>();
            if (projectLogList != null && !projectLogList.isEmpty()) {
                for (HashMap<String,Object>  resultMap : projectLogList) {
                    projectLogIds.add((Integer)resultMap.get("logId"));
                }
                HashMap<String, List<HashMap<String, Object>>> viewHistory = viewStatisService.getHistory(projectLogIds);
                for (HashMap<String,Object> projectLog : projectLogList ){
                    projectLog.put("viewHistory",viewHistory.get(projectLog.get("logId").toString()));
                }
                map.put("record",projectLogList);
            } else {
                map.put("record", null);
            }
            map.put("total", projectLogList.size());
            map.put("code",0);
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
            map.put("total", projectLogList.size());
            map.put("code",0);
        }

        return map;
    }

    @ApiOperation("管理员获取当前部门内所有用户下拉框内容")
    @GetMapping("/getUserSelection")
    public HashMap<String,Object> getUserSelection(HttpServletRequest request){
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            map.put("selection",wxUserService.selectMaps(new EntityWrapper<WxUser>().setSqlSelect("user_id, name").in("organization_id",ids)));
            map.put("code", 0);
        }
        return map;
    }

    @ApiOperation("管理员获取当前部门内所有管理员下拉框内容")
    @GetMapping("/getAdminSelection")
    public HashMap<String,Object> getAdminSelection(HttpServletRequest request){
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            map.put("selection",wxUserService.getAdminList(ids));
            map.put("code", 0);
        }
        return map;
    }

    @ApiOperation("管理员获取当前部门内所有项目下拉框内容")
    @GetMapping("/getProjectSelection")
    public HashMap<String,Object> getProjectSelection(HttpServletRequest request){
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            map.put("selection", projectService.getProjectSelection(ids));
            map.put("code", 0);
        }
        return map;
    }

    @ApiOperation("检查项目名字是否合法")
    @RequestMapping(value = "checkProjectName", method = RequestMethod.POST )
    public boolean checkProjectNames(@RequestBody HashMap<String, String> projectForm) {
        String projectName = projectForm.get("projectName");
        if (projectName == null || projectName.equals(""))
            return false;
        Project project = projectService.selectOne(new EntityWrapper<Project>().eq("project_name",projectName));
        if(project != null)
            return false;
        else
            return true;
    }

    @ApiOperation("管理员添加日志")
    @PostMapping("/addProjectLog")
    public HashMap<String,Object> adminAddProjectLog(HttpServletRequest request,
                                                      @RequestBody ProjectLog projectLog) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            if (projectLog.getProjectId() == null) {
                map.put("code",3);
                map.put("msg", "请选择日志");
            } else {
                List<Integer> ids = getOrganizationIds(administrator.getOid());
                Integer oid = projectService.getOrganizationId(projectLog.getProjectId());
                if (ids.contains(oid)) {
                    if (projectLog.getDate() != null && projectLog.getContent() != null && projectLog.getUserId() != null) {
                        projectLogService.insert(projectLog);
                        map.put("code", 0);
                    } else {
                        map.put("code",3);
                        map.put("msg", "请正确填写日志信息");
                    }
                } else {
                    map.put("code", 2);
                    map.put("msg", "您暂无发布该项目下日志的权限");
                }
            }
        }
        return map;
    }

    @ApiOperation("管理员编辑日志")
    @PostMapping("/editProjectLog")
    public HashMap<String,Object> adminEditProjectLog(HttpServletRequest request,
                                                   @RequestBody ProjectLog projectLog) {
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        HashMap<String,Object> map = new HashMap<>();
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            List<Integer> ids = getOrganizationIds(administrator.getOid());
            Integer oid = projectService.getOrganizationId(projectLog.getProjectId());
//            System.out.println(projectLog.getProjectId());
//            System.out.println("projectLog = ");
//            System.out.println(projectLog);
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
    @PostMapping("/deleteProjectLog/{logId}")
    public HashMap<String,Object> adminDeleteProjectLog(HttpServletRequest request,
                                                      @PathVariable Integer logId) {
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

    @ApiOperation("批量添加用户")
    @RequestMapping(value = "/addStaffs", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String,Object> addStaffs(@RequestBody HashMap<String, Object> staff,
                                            HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            Integer projectId = (Integer) staff.get("projectId");
            List<Integer> userIds = (List<Integer>)staff.get("userIds");
            if(groupService.addStaffs(projectId, userIds)) {
                map.put("code", 0);
            } else {
                map.put("code", 1);
                map.put("msg", "添加失败！");
            }
        }
        return map;
    }

    @ApiOperation("添加单个用户")
    @RequestMapping(value = "addStaff", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String,Object> addStaff(@RequestBody HashMap<String, Object> staff,
                                           HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            Group group = new Group();
            group.setProjectId((Integer) staff.get("projectId"));
            group.setUserId((Integer)staff.get("userId"));
            if (groupService.insert(group)) {
                map.put("code", 0);
            } else {
                map.put("code", 1);
                map.put("msg", "添加失败！");
            }
        }
        return map;
    }

    @ApiOperation("删除用户")
    @RequestMapping(value = "deleteStaff", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String,Object> deleteStaff(@RequestBody HashMap<String, Object> staff,
                                              HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        Administrator administrator = (Administrator) request.getSession().getAttribute("admin");
        if(administrator == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重新登录后再试！");
        }else {
            String userName = staff.get("userName").toString();
            WxUser wxUser = wxUserService.selectOne(new EntityWrapper<WxUser>().eq("name", userName));
            if (groupService.delete(new EntityWrapper<Group>().eq("project_id", staff.get("projectId")).eq("user_id", wxUser.getUserId()))) {
                map.put("code", 0);
            } else {
                map.put("code", 1);
                map.put("msg", "删除失败！");
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

