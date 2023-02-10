package com.syt.yygh.hosp.controller;

import com.syt.yygh.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import syt.hospital.model.hosp.HospitalSet;

import java.util.List;

/**
 * @Author: foofoo3
 */

@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @GetMapping("findAll")
    public List<HospitalSet> findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

    @DeleteMapping("{id}")
    public boolean removeHospSet(@PathVariable Long id){
        boolean removeById = hospitalSetService.removeById(id);
        return removeById;
    }

}
