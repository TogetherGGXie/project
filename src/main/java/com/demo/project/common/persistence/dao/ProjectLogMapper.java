package com.demo.project.common.persistence.dao;

import com.demo.project.common.persistence.template.modal.ProjectLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

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
}
