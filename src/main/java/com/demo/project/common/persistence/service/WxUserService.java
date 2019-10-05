package com.demo.project.common.persistence.service;

import com.demo.project.common.persistence.modal.WxUser;
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
public interface WxUserService extends IService<WxUser> {
  List<HashMap<String, Object>> getUserList(List<Integer> organizationIds);
  List<HashMap<String, Object>> getAdminList(List<Integer> organizationIds);
  HashMap<String, Object> getUserInfo(Integer userId);
}
