package com.demo.project.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.demo.project.common.persistence.template.modal.ProjectLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-12
 */
@Repository
public interface ProjectLogMapper extends BaseMapper<ProjectLog> {

    @Insert("INSERT INTO project_log ( project_id, user_id, date, content, pics )\n" +
            "VALUES\n" +
            "\t( #{projectId}, #{userId}, #{date}, #{content}, #{pics})")
    @Options(useGeneratedKeys=true, keyColumn="log_id",keyProperty = "log_id")
//    Integer addLog(@Param("keyword") Integer projectId,
//                   @Param("userId") Integer userId,
//                   @Param("date") Date date,
//                   @Param("content") String content,
//                   @Param("pics") String pics);
        Integer addLog(ProjectLog projectLog);

    @Select("SELECT\n" +
            "\tproject_log.log_id,\n" +
            "\twx_user.NAME,\n" +
            "\tproject_log.date,\n" +
            "\tproject_log.content,\n" +
            "\tproject_log.pics \n" +
            "FROM\n" +
            "\t`project_log`\n" +
            "\tJOIN wx_user ON project_log.user_id = wx_user.user_id \n" +
            "WHERE\n" +
            "\tproject_log.project_id = #{projectId}\n" +
            "ORDER BY\n" +
            "\tproject_log.date DESC")
    @Results(id="LogsResultMap",value={
            @Result(property = "logId", column = "log_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "userName", column = "NAME"),
            @Result(property = "content", column = "content"),
            @Result(property = "pics", column = "pics"),
    })
    List<HashMap<String,Object>> getLogs(Pagination pagination, @Param("projectId") Integer projectId);
}
