package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.template.modal.Project;
import com.demo.project.common.persistence.dao.ProjectMapper;
import com.demo.project.common.persistence.service.ProjectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
