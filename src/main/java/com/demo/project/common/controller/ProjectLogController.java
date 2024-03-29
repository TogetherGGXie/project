package com.demo.project.common.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.modal.Group;
import com.demo.project.common.persistence.service.GroupService;
import com.demo.project.common.persistence.service.KeywordsService;
import com.demo.project.common.persistence.service.ProjectLogService;
import com.demo.project.common.persistence.modal.Keywords;
import com.demo.project.common.persistence.modal.ProjectLog;
import com.demo.project.common.persistence.modal.WxUser;
import com.demo.project.common.persistence.service.ProjectService;
import com.hankcs.hanlp.HanLP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    private KeywordsService keywordsService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ProjectService projectService;

    @ApiOperation(value = "添加日志")
    @RequestMapping(value = "/addLog", method = RequestMethod.POST)
    @ResponseBody
    public Object addLog(@RequestParam(value = "pics",required = false) String pics,
                         @RequestParam("date") String date,
                         @RequestParam("projectId") Integer projectId,
                         @RequestParam("content") String content,
                         HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重启小程序");
            return  map;
        }else if (wxUser.getStatus() == 0) {
            map.put("code",3);
            map.put("msg","您的账号未激活，请与管理员联系");
            return map;
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
        projectLog.setCreateTime(new Date());
        projectLog.setLastUpdTime(new Date());
        projectLogService.insert(projectLog);
        map.put("logId",projectLog.getLogId());
        map.put("projectId",projectLog.getProjectId());
        map.put("code",0);
        String text = projectLogService.getLogContents(projectId);
        List<String> keywords = HanLP.extractKeyword(text, 3);
        Keywords key = new Keywords();
        key.setProjectId(projectId);
        key.setKeywords(String.join(",",keywords));
        keywordsService.insertOrUpdate(key);
        return map;
    }


    @ApiOperation(value = "获取项目的日志列表")
    @RequestMapping(value = "/getLogs", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> getLogs(@RequestParam(value = "pageNumber", required = false) Integer page,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                    @RequestParam(value = "projectId") Integer projectId,
                                                    @RequestParam(value = "keyword", required = false) String keyword,
                                                 HttpServletRequest request
    ) {
        HashMap<String,Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser == null) {
            map.put("code",1);
            map.put("msg","登录状态失效，请重新启动小程序");
            return map;
        }else if (groupService.selectOne(new EntityWrapper<Group>().eq("project_id",projectId)
                .eq("user_id",wxUser.getUserId())) == null
                && (projectService.selectById(projectId).getLeaderId() != wxUser.getUserId())) {
            map.put("code",2);
            map.put("msg","您暂无查看此项目的权限，请与管理员联系");
            return map;
        }else if (wxUser.getStatus() == 0) {
            map.put("code",3);
            map.put("msg","您的账号未激活，请与管理员联系");
            return map;
        }else {
            if (page == null)
                page = 1;
            if (pageSize == null)
                pageSize = 10;
            if (keyword == null)
                keyword = "";
            Page<HashMap<String, Object>> pager = projectLogService.getLogs(new Page<>(page, pageSize), projectId, keyword);
            map.put("code", 0);
            map.put("records", pager.getRecords());
            map.put("pages", pager.getPages());
            map.put("total", pager.getTotal());
            return map;
        }
    }
}

