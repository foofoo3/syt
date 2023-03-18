package com.syt.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syt.hospital.model.user.Patient;

import java.util.List;

/**
 * @Author: foofoo3
 */
public interface PatientService extends IService<Patient> {
    List<Patient> findAllUserId(Long userId);

    Patient getPatientId(Long id);
}
