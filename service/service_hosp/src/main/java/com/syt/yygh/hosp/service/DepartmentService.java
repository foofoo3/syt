package com.syt.yygh.hosp.service;

import org.springframework.data.domain.Page;
import syt.hospital.model.hosp.Department;
import syt.hospital.vo.hosp.DepartmentQueryVo;
import syt.hospital.vo.hosp.DepartmentVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface DepartmentService {
    void save(Map<String, Object> paramMap);

    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);


    List<DepartmentVo> findDeptTree(String hoscode);
}
