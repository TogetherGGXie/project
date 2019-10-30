package com.demo.project.common.persistence.service.serviceImpl;

import com.baomidou.mybatisplus.plugins.Page;
import com.demo.project.common.persistence.modal.ProjectLog;
import com.demo.project.common.persistence.dao.ProjectLogMapper;
import com.demo.project.common.persistence.service.ProjectLogService;
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
public class ProjectLogServiceImpl extends ServiceImpl<ProjectLogMapper, ProjectLog> implements ProjectLogService {

    @Override
    public Integer addLog(ProjectLog projectLog) {
        return projectLogMapper.addLog(projectLog);
    }

    @Override
    public Page<HashMap<String, Object>> getLogs(Page<HashMap<String, Object>> pager, Integer projectId, String keyword) {
        if(keyword == null || keyword.equals("null") || keyword.equals(""))
            keyword = "[\\w]*";
        else
            keyword = keyword.trim().replaceAll("\\s+","|");
        Page<HashMap<String, Object>> page = new Page<>(pager.getCurrent(),pager.getSize());
        return page.setRecords(projectLogMapper.getLogs(page, projectId, keyword));
    }

    @Override
    public Boolean updateViewTimes(Integer logId) {
        return projectLogMapper.updateViewTimes(logId);
    }

    @Override
    public String getLogContents(Integer projectId) {
        List<String> contents = projectLogMapper.getLogContents(projectId);
        StringBuilder stringBuilder = new StringBuilder();
        for (String content : contents) {
            stringBuilder.append(content);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public List<HashMap<String, Object>> getProjectLogList(List<Integer> organizationIds) {
        return projectLogMapper.getProjectLogList(organizationIds);
    }

    @Override
    public List<HashMap<String, Object>> getLogList(Integer projectId) {
        return projectLogMapper.getLogList(projectId);
    }

    @Autowired
    private ProjectLogMapper projectLogMapper;
}
