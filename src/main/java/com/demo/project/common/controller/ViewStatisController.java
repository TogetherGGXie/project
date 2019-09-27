package com.demo.project.common.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.project.common.persistence.service.ProjectLogService;
import com.demo.project.common.persistence.service.ViewStatisService;
import com.demo.project.common.persistence.modal.ViewStatis;
import com.demo.project.common.persistence.modal.WxUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-29
 */
@Api("浏览记录管理")
@Controller
@RequestMapping("/viewStatis")
public class ViewStatisController {

    @Autowired
    private ViewStatisService viewStatisService;
    @Autowired
    private ProjectLogService projectLogService;

    @ApiOperation("添加浏览记录")
    @RequestMapping(value = "/addView", method = RequestMethod.POST)
    @ResponseBody
    public Object addView(@RequestParam("logId") Integer logId,
                          HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if(wxUser == null){
            map.put("code", 1);
            map.put("msg", "登录状态失效，请重启小程序");
        }else if (wxUser.getStatus() == 0) {
            map.put("code",3);
            map.put("msg","您的账号未激活，请与管理员联系");
            return map;
        }else {
            ViewStatis viewStatis = new ViewStatis();
            viewStatis.setLogId(logId);
            viewStatis.setUserId(wxUser.getUserId());
            ViewStatis vs = viewStatisService.selectOne(new EntityWrapper<ViewStatis>().eq("log_id",logId).eq("user_id",wxUser.getUserId()));
            if(vs != null) {
                vs.setViewTime(DateUtil.date());
                viewStatisService.updateById(vs);
            }
            else {
                viewStatis.setViewTime(DateUtil.date());
                viewStatisService.insert(viewStatis);
                projectLogService.updateViewTimes(logId);
            }
            map.put("code", 0);
        }
        return map;
    }

    @ApiOperation("获取浏览记录")
    @ResponseBody
    @RequestMapping(value = "/getViewHistory",method = RequestMethod.GET)
    public Object getViewHistory(@RequestParam(name = "logId")Integer logId) {
        return viewStatisService.getViewHistory(logId);
    }

}

