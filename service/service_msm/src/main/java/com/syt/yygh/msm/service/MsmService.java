package com.syt.yygh.msm.service;

import syt.hospital.vo.msm.MsmVo;

/**
 * @Author: foofoo3
 */
public interface MsmService {
    //发送手机验证码
    boolean send(String phone, String code);

    //mq使用发送短信
    boolean send(MsmVo msmVo);
}
