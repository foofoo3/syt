package com.syt.yygh.hosp.service;

import org.springframework.data.domain.Page;
import syt.hospital.model.hosp.Hospital;
import syt.hospital.vo.hosp.HospitalQueryVo;

import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface HospitalService {
    void save(Map<String, Object> paramMap);

    Hospital getByHoscode(String hoscode);

    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);
}
