package com.demo.project.common.persistence.dao;

import com.demo.project.common.persistence.modal.ViewStatis;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-29
 */
@Repository
public interface ViewStatisMapper extends BaseMapper<ViewStatis> {

    @Select("SELECT\n" +
            "\twx_user.NAME,\n" +
            "\tview_statis.view_time\n" +
            "FROM\n" +
            "\t`view_statis`\n" +
            "\tJOIN wx_user ON view_statis.user_id = wx_user.user_id \n" +
            "WHERE\n" +
            "\tview_statis.log_id = #{logId}\n" +
            "ORDER BY\n" +
            "\tview_statis.view_time DESC")
    @Results(id="ViewHistoryResultMap",value={
            @Result(property = "name", column = "Name"),
            @Result(property = "viewTime", column = "view_time")
    })
    List<HashMap<String,Object>> getViewHistory(@Param("logId") Integer logId);

    @Select("<script>" +
            "\tSELECT view_statis.log_id, wx_user.name, view_statis.view_time \n" +
            "\tfrom view_statis \n" +
            "\tjoin wx_user \n" +
            "\ton wx_user.user_id = view_statis.user_id \n" +
            "\twhere view_statis.log_id in \n" +
            "\t<foreach collection='logIds' item='logId' index='index'  open = '(' close = ')' separator=','> \n" +
            "#{logId} \n" +
            "</foreach> \n" +
            "</script>")
    @Results(id="UserListResultMap",value={
            @Result(property = "logId", column = "log_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "viewTime", column = "view_time"),
    })
    List<HashMap<String, Object>> getHistory(@Param("logIds") List<Integer> logIds);
}
