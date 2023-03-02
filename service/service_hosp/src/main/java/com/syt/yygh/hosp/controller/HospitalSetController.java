package com.syt.yygh.hosp.controller;

import com.syt.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import syt.hospital.model.hosp.HospitalSet;

import java.util.List;

/**
 * @Author: foofoo3
 */

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public List<HospitalSet> findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("{id}")
    public boolean removeHospSet(@PathVariable Long id){
        boolean removeById = hospitalSetService.removeById(id);
        return removeById;
    }

}
