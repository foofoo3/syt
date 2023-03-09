package com.syt.yygh.hosp.service;

import syt.hospital.model.hosp.Hospital;

import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface HospitalService {
    void save(Map<String, Object> paramMap);

    Hospital getByHoscode(String hoscode);
}
