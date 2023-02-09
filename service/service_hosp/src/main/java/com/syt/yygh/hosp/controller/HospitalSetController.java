package com.syt.yygh.hosp.controller;

import com.syt.yygh.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: foofoo3
 */

@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;
    
}
