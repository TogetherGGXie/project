package com.demo.project.common.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.project.common.persistence.modal.Organization;
import com.demo.project.common.persistence.service.OrganizationService;
import com.demo.project.common.persistence.service.WxUserService;
import com.demo.project.common.persistence.modal.WxUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
 * @since 2019-08-12
 */
@Api("微信用户管理")
@Controller
@RequestMapping("/wxUser")
public class WxUserController {

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private OrganizationService organizationService;

    @ApiOperation("修改用户信息")
    @ResponseBody
    @RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
    public Object editName(@RequestParam(value = "userName") String newName,
                           @RequestParam(value = "authority") Integer authority,
                           @RequestParam(value = "organizationId") Integer organizationId,
                            HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser==null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重启小程序");
            return map;
        }
        if (newName != null && newName != "" && !newName.equals(""))
            wxUser.setName(newName);
        if (authority != wxUser.getAuthority()) {
            wxUser.setAuthority(authority);
            wxUser.setStatus(0);
        }
        if (organizationId != wxUser.getOrganizationId()) {
            wxUser.setOrganizationId(organizationId);
            wxUser.setStatus(0);
        }
        System.out.println(wxUser.toString());
        if(wxUserService.updateById(wxUser)){
            request.getSession().setAttribute("user",wxUser);
            map.put("code",0);
        } else {
            map.put("code",1);
            map.put("msg", "修改失败");
        }
        request.getSession().setAttribute("user", wxUser);
        return map;
    }


    @ApiOperation("微信用户登陆")
    @ResponseBody
    @RequestMapping(value = "/decodeUserInfo", method = RequestMethod.GET)
    public Map decodeUserInfo(@RequestParam(value = "code") String code,
                              HttpServletRequest request){
        System.out.println(code);
        Map map = new HashMap();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("code", 0);
            map.put("msg", "code 不能为空");
            return map;
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String appId = "wxb7797effd51eea44";  //appId
        //小程序的 app secret (在微信小程序管理后台获取)
        String appSecret = "4ca3b2cc82da0a6013b1d3011c370db2";  //appSecret
        //授权（必填）
        String grant_type = "authorization_code";
        String path = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code +
                "&grant_type=" + grant_type;
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;

            /**设置URLConnection的参数和普通的请求属性****start***/

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**设置URLConnection的参数和普通的请求属性****end***/

            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");//GET和POST必须全大写
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
            conn.connect();

            /**GET方法请求*****end*/

            /***POST方法请求****start*/

            /*out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流

            out.print(data);//发送请求参数即数据

            out.flush();//缓冲数据
            */
            /***POST方法请求****end*/

            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                str=new String(str.getBytes(),"UTF-8");//解决中文乱码问题
                System.out.println(str);
                sb.append(str);
            }
            String result = sb.toString();
//            System.out.println("string="+result);
//            System.out.println("sessionId="+request.getSession().getId());
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            JSONObject  jsonObject = JSONObject.parseObject(result);
            Map<String,Object> mapper = (Map<String,Object>)jsonObject;
//            System.out.println("map="+jsonObject.toString());
            if(mapper.get("errcode") != null) {
                map.put("msg", "登录失败，请重试");
                map.put("code", 1);
                return map;
            }
            WxUser wxUser = wxUserService.selectOne(new EntityWrapper<WxUser>().eq("open_id",mapper.get("openid")));;
            if (wxUser == null){
                wxUser = new WxUser();
                wxUser.setAuthority(0);
                wxUser.setOpenId(mapper.get("openid").toString());
                wxUser.setStatus(0);
                wxUser.setCreateTime(DateUtil.date());
                wxUserService.insert(wxUser);
            }
            request.getSession().setAttribute("user",wxUser);
//            System.out.println(wxUser.toString());
//            System.out.println("session ="+request.getSession().getAttribute("user"));
            map.put("sessionId",request.getSession().getId());
            map.put("authority",wxUser.getAuthority());
            map.put("userName",wxUser.getName());
            map.put("status", wxUser.getStatus());
            map.put("organization", wxUser.getOrganizationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("code", 0);
        return map;
    }

    @ApiOperation("获取部门及子部门的用户")
    @RequestMapping(value = "getUserSelection", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> getUsers(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser==null) {
            map.put("code",1);
            map.put("msg", "登录状态失效，请重启小程序");
            return map;
        }
        List<Map<String, Object>> oids = organizationService.selectMaps(new EntityWrapper<Organization>().setSqlSelect("id,pid").orderBy("id"));
        List<Integer> ids = new ArrayList<>();
        ids.add(wxUser.getOrganizationId());
        for(Map<String, Object> organization : oids) {
            if(ids.contains(organization.get("pid")))
                ids.add((Integer)organization.get("id"));
        }
        map.put("code", 0);
        map.put("userSelection", wxUserService.getUserSelection(ids));
        return map;
    }

    @ApiOperation("获取所有用户")
    @RequestMapping(value = "getAllUsers", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getAllUsers() {
        return wxUserService.selectMaps(new EntityWrapper<WxUser>().setSqlSelect("user_id,name,authority,organization_id").eq("status",1));
    }

    @ApiOperation("获取用户个人信息")
    @GetMapping("/getUserInfo")
    @ResponseBody
    public HashMap<String, Object> getUserInfo(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        WxUser wxUser = (WxUser) request.getSession().getAttribute("user");
        if (wxUser == null) {
            map.put("code",1);
            map.put("msg", "登录状态失效");
        }else {
            map.put("userInfo", wxUserService.getUserInfo(wxUser.getUserId()));
            map.put("code",0);
        }
        return map;
    }
}

