package com.demo.project.common.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.project.common.persistence.modal.Group;
import com.demo.project.common.persistence.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-06
 */
@Api("分组管理器")
@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @ApiOperation("批量添加用户")
    @RequestMapping(value = "addStaffs", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String,Object> addStaffs(@RequestParam(value = "projectId") Integer projectId,
                                            @RequestParam(value = "userIds") List<Integer> userIds ) {
        HashMap<String,Object> map = new HashMap<>();
        if(groupService.addStaffs(projectId, userIds)) {
            map.put("code", 0);
            return map;
        } else {
            map.put("code", 1);
            map.put("msg","添加失败！");
            return map;
        }
    }

    @ApiOperation("添加单个用户")
    @RequestMapping(value = "addStaff", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String,Object> addStaff(@RequestParam(value = "projectId") Integer projectId,
                                            @RequestParam(value = "userId") Integer userId ) {
        HashMap<String,Object> map = new HashMap<>();
        Group group = new Group();
        group.setProjectId(projectId);
        group.setUserId(userId);
        if(groupService.insert(group)) {
            map.put("code", 0);
            return map;
        } else {
            map.put("code", 1);
            map.put("msg","添加失败！");
            return map;
        }
    }

    @ApiOperation("删除用户")
    @RequestMapping(value = "deleteStaff", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String,Object> deleteStaff(@RequestParam(value = "projectId") Integer projectId,
                                           @RequestParam(value = "userId") Integer userId ) {
        HashMap<String,Object> map = new HashMap<>();
        if(groupService.delete(new EntityWrapper<Group>().eq("project_id",projectId).eq("user_id",userId))) {
            map.put("code", 0);
            return map;
        } else {
            map.put("code", 1);
            map.put("msg","删除失败！");
            return map;
        }
    }
    


}

