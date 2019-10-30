package com.demo.project.common.persistence.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.modal.ProjectLog;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

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
    Page<HashMap<String, Object>> getLogs(Page<HashMap<String, Object>> pager, Integer projectId, String keyword);
    Boolean updateViewTimes(Integer logId);
    String getLogContents(Integer projectId);
    List<HashMap<String, Object>> getProjectLogList(List<Integer> organizationIds);
    List<HashMap<String, Object>> getLogList(Integer projectId);
}
