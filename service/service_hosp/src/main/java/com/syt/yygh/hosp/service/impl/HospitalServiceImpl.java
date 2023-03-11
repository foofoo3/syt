package com.syt.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.syt.yygh.cmn.client.DictFeignClient;
import com.syt.yygh.hosp.repository.HospitalRepository;
import com.syt.yygh.hosp.service.HospitalService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import syt.hospital.model.hosp.Hospital;
import syt.hospital.model.hosp.Schedule;
import syt.hospital.vo.hosp.HospitalQueryVo;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: foofoo3
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> paramMap) {
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString,Hospital.class);

        String hoscode = hospital.getHoscode();
        Hospital hospitalGet = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospitalGet != null) {
            hospital.setStatus(hospitalGet.getStatus());
            hospital.setCreateTime(hospitalGet.getCreateTime());
        }else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
        }
        hospital.setUpdateTime(new Date());
        hospital.setIsDeleted(0);
        hospitalRepository.save(hospital);
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospitalByHoscode = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospitalByHoscode;
    }

    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        Pageable pageable = PageRequest.of(page-1,limit);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);

        Hospital hospital = new Hospital();
        if (hospitalQueryVo != null) {
            BeanUtils.copyProperties(hospitalQueryVo,hospital);
        }

        Example<Hospital> example = Example.of(hospital,matcher);

        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);

        List<Hospital> content = pages.getContent();
        content.stream().forEach(item -> {
            this.setHospitalHosType(item);
        });

        return pages;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        //根据id查询医院信息
        Hospital hospital = hospitalRepository.findById(id).get();
        //设置修改的值
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public Map<String, Object> getHospById(String id) {

        Map<String, Object> result = new HashMap<>();
        Hospital hospital = this.setHospitalHosType(hospitalRepository.findById(id).get());
        //医院基本信息（包含医院等级）
        result.put("hospital",hospital);
        //单独处理更直观
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);

        return result;

    }

    private Hospital setHospitalHosType(Hospital hospital) {
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());

        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        Map<String, Object> param = hospital.getParam();
        param.put("fullAddress",provinceString+cityString+districtString);
        param.put("hostypeString",hostypeString);

        return hospital;
    }
}
