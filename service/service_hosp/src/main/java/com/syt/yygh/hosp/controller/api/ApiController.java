package com.syt.yygh.hosp.controller.api;

import com.syt.yygh.common.helper.HttpRequestHelper;
import com.syt.yygh.common.result.Result;
import com.syt.yygh.hosp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: foofoo3
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;

    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        Map<String, String[]> requstMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requstMap);
        hospitalService.save(paramMap);
        return Result.ok();
    }
}
