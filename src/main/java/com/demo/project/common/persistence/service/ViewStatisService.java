package com.demo.project.common.persistence.service;

import com.demo.project.common.persistence.modal.ViewStatis;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-29
 */
public interface ViewStatisService extends IService<ViewStatis> {
    List<HashMap<String,Object>> getViewHistory(Integer logId);
}
