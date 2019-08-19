package com.demo.project.common.persistence.service;

import com.demo.project.common.persistence.template.modal.ProjectLog;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-12
 */
public interface ProjectLogService extends IService<ProjectLog> {
    Integer addLog(ProjectLog projectLog);
}
