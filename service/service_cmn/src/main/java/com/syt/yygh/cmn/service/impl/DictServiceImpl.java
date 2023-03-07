package com.syt.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.yygh.cmn.listener.DictListener;
import com.syt.yygh.cmn.mapper.DictMapper;
import com.syt.yygh.cmn.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import syt.hospital.model.cmn.Dict;
import syt.hospital.vo.cmn.DictEeVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: foofoo3
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        for (Dict dict : dicts) {
            boolean children = isChildren(dict.getId());
            dict.setHasChildren(children);
        }
        return dicts;
    }

    @Override
    public void exportDict(HttpServletResponse response) {
//        设置下载信息
        response.setContentType("appliction/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "Dict";
        response.setHeader("Content-disposition","attachment;filename=" + fileName + ".xlsx");

        List<Dict> dicts = baseMapper.selectList(null);

        ArrayList<DictEeVo> list = new ArrayList<>();
        for (Dict dict : dicts) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            list.add(dictEeVo);
        }

        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importDict(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class, new DictListener(baseMapper))
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer integer = baseMapper.selectCount(queryWrapper);
        return integer>0;
    }


}
