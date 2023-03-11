package com.syt.yygh.cmn.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.syt.yygh.cmn.service.DictService;
import com.syt.yygh.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import syt.hospital.model.cmn.Dict;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: foofoo3
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {
    @Autowired
    private DictService dictService;

    @ApiOperation("根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> dictList = dictService.findChildData(id);
        return Result.ok(dictList);
    }

    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response) {
        dictService.exportDict(response);
    }

    @PostMapping("importData")
    public Result importDict(MultipartFile file) {
        dictService.importDict(file);
        return Result.ok();
    }

    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable("dictCode") String dictCode,
                          @PathVariable("value") String value) {
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    @GetMapping("getName/{value}")
    public String getName(@PathVariable("value") String value) {
        String dictName = dictService.getDictName("",value);
        return dictName;
    }

    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }
}
