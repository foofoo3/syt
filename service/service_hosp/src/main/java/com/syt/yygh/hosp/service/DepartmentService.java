package com.syt.yygh.hosp.service;

import org.springframework.data.domain.Page;
import syt.hospital.model.hosp.Department;
import syt.hospital.vo.hosp.DepartmentQueryVo;

import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface DepartmentService {
    void save(Map<String, Object> paramMap);

    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);
}
