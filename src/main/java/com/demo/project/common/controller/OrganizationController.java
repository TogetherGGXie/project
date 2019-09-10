package com.demo.project.common.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.project.common.persistence.service.OrganizationService;
import com.demo.project.common.persistence.modal.Organization;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-06
 */
@Api("组织管理")
@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @ApiOperation("获取子部门")
    @RequestMapping(value = "/getDepartment",method = RequestMethod.GET)
    @ResponseBody
    public Object getDepartment(@RequestParam(value = "pid") Integer pid) {
        return organizationService.selectMaps(new EntityWrapper<Organization>().setSqlSelect("id,name").eq("pid",pid));
//        return organizationService.selectList(new EntityWrapper<Organization>().setSqlSelect("id,name").eq("pid",pid));
    }

}

