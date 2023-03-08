package com.syt.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.syt.yygh.hosp.repository.HospitalRepository;
import com.syt.yygh.hosp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import syt.hospital.model.hosp.Hospital;

import java.util.Date;
import java.util.Map;

/**
 * @Author: foofoo3
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString,Hospital.class);

        String hoscode = hospital.getHoscode();
        Hospital hospitalGet = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospitalGet != null) {
            hospital.setStatus(hospitalGet.getStatus());
            hospital.setCreateTime(hospitalGet.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }
}
