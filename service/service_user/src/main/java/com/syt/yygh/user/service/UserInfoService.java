package com.syt.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syt.hospital.model.user.UserInfo;
import syt.hospital.vo.user.LoginVo;

import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> loginUser(LoginVo loginVo);
    //根据openid判断
    UserInfo selectWxInfoOpenId(String openid);
}
