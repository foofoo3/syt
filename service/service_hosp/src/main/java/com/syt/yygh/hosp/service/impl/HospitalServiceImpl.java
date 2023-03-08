package com.syt.yygh.hosp.service.impl;

import com.syt.yygh.hosp.repository.HospitalRepository;
import com.syt.yygh.hosp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: foofoo3
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

}
