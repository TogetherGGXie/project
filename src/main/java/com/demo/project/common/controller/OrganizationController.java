package com.demo.project.common.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.project.common.persistence.service.OrganizationService;
import com.demo.project.common.persistence.modal.Organization;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
 * @since 2019-09-06
 */
@Api("组织管理")
@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;


    @ApiOperation("获取组织")
    @RequestMapping(value = "/getOrganization",method = RequestMethod.GET)
    @ResponseBody
    public Object getOrganization() {
        return organizationService.selectMaps(new EntityWrapper<Organization>().setSqlSelect("id,name").eq("pid", 0));
    }


    @ApiOperation("获取子部门")
    @RequestMapping(value = "/getDepartment",method = RequestMethod.GET)
    @ResponseBody
    public Object getDepartment(@RequestParam(name = "organizationId") Integer organizationId) {
        return organizationService.selectMaps(new EntityWrapper<Organization>().setSqlSelect("id,name").eq("pid", organizationId));
    }

//    @ApiOperation("获取子部门")
//    @RequestMapping(value = "/getDepartment",method = RequestMethod.GET)
//    @ResponseBody
//    public Object getDepartment(@RequestParam(name = "organizationId") Integer organizationId) {
//        List<Map<String, Object>> organizations = organizationService.selectMaps(new EntityWrapper<Organization>());
//        List<Map<String, Object>> result = new ArrayList<>();
//        for(Map<String, Object> organization : organizations) {
//            if ((Integer)organization.get("pid") == 0) {
//                List<Map<String, Object>> departments = new ArrayList<>();
//                for (Map<String, Object> o : organizations) {
//                    if ((Integer)o.get("pid") == (Integer)organization.get("id")) {
//                        departments.add(o);
//                    }
//                }
//                organization.put("departments", departments);
//                result.add(organization);
//            }
//        }
//        return result;
////        return organizationService.selectList(new EntityWrapper<Organization>().setSqlSelect("id,name").eq("pid",pid));
//    }

}

