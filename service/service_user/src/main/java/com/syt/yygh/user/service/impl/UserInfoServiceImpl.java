package com.syt.yygh.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.yygh.user.mapper.UserInfoMapper;
import com.syt.yygh.user.service.UserInfoService;
import org.springframework.stereotype.Service;
import syt.hospital.model.user.UserInfo;

/**
 * @Author: foofoo3
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
