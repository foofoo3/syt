package com.syt.yygh.hosp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import syt.hospital.model.hosp.HospitalSet;
import syt.hospital.vo.order.SignInfoVo;

/**
 * @Author: foofoo3
 */
public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);

    SignInfoVo getSignInfoVo(String hoscode);

}
