package com.demo.project.common.persistence.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.template.modal.Project;
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
    Page<HashMap<String, Object>> getProjects(Page<HashMap<String, Object>> pager, String keyword);
    List<HashMap<String, Object>> getProjectNames();
    HashMap<String, Object> getProject(Integer projectId);
}
