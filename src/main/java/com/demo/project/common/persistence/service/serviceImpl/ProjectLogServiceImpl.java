package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.template.modal.Project;
import com.demo.project.common.persistence.template.modal.ProjectLog;
import com.demo.project.common.persistence.dao.ProjectLogMapper;
import com.demo.project.common.persistence.service.ProjectLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-12
 */
@Service
public class ProjectLogServiceImpl extends ServiceImpl<ProjectLogMapper, ProjectLog> implements ProjectLogService {

    @Override
    public Integer addLog(ProjectLog projectLog) {
        return projectLogMapper.addLog(projectLog);
    }

    @Autowired
    private ProjectLogMapper projectLogMapper;
}
