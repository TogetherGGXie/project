package com.demo.project.common.persistence.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.modal.Project;
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
public interface ProjectService extends IService<Project> {
    Page<HashMap<String, Object>> getProjects(Page<HashMap<String, Object>> pager, String keyword, Integer userId);
    List<HashMap<String, Object>> getProjectNames(List<Integer> projectIds, Integer userId);
    HashMap<String, Object> getProject(Integer projectId);
    List<HashMap<String, Object>> getProjectList(List<Integer> organizationIds);
    List<HashMap<String, Object>> getProjectSelection(List<Integer> organizationIds);
    Integer getOrganizationId(Integer projectId);
}
