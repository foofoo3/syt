package com.syt.yygh.hosp.controller;

import com.syt.yygh.common.result.Result;
import com.syt.yygh.hosp.service.HospitalService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import syt.hospital.model.hosp.Hospital;
import syt.hospital.vo.hosp.HospitalQueryVo;

import java.util.Map;


/**
 * @Author: foofoo3
 */
@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class Hospitalcontroller {
    @Autowired
    private HospitalService hospitalService;

    @GetMapping("list/{page}/{limit}")
    public Result findPageHosp(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               @RequestBody(required = false) HospitalQueryVo hospitalQueryVo) {

        Page<Hospital> pageModel = hospitalService.selectHospPage(page,limit,hospitalQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation(value = "更新医院上线状态")
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id,@PathVariable Integer status) {
        hospitalService.updateStatus(id,status);
        return Result.ok();
    }

    @ApiOperation(value = "医院详情信息")
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id) {
        Map<String, Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }
}
