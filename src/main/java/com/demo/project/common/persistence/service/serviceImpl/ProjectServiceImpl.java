package com.demo.project.common.persistence.service.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.template.modal.Project;
import com.demo.project.common.persistence.dao.ProjectMapper;
import com.demo.project.common.persistence.service.ProjectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-12
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public Page<HashMap<String, Object>> getProjects(Page<HashMap<String, Object>> pager, String keyword) {
        if(keyword == null || keyword.equals("null") || keyword.equals(""))
            keyword = "[\\w]*";
        else
            keyword = keyword.trim().replaceAll("\\s+","|");
        Page<HashMap<String, Object>> page = new Page<>(pager.getCurrent(),pager.getSize());
        return page.setRecords(projectMapper.getProjects(page, keyword));
    }

    @Override
    public List<HashMap<String, Object>> getProjectNames() {
        return projectMapper.getProjectNames();
    }

    @Autowired
    private ProjectMapper projectMapper;
}
