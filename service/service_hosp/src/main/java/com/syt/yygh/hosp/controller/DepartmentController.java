package com.syt.yygh.hosp.controller;

import com.syt.yygh.common.result.Result;
import com.syt.yygh.hosp.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import syt.hospital.vo.hosp.DepartmentVo;

import java.util.List;

/**
 * @Author: foofoo3
 */
@RestController
@RequestMapping("/admin/hosp/department")
//@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }


}
