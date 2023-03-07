package com.syt.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.syt.yygh.cmn.mapper.DictMapper;
import org.springframework.beans.BeanUtils;
import syt.hospital.model.cmn.Dict;
import syt.hospital.vo.cmn.DictEeVo;

/**
 * @Author: foofoo3
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    private DictMapper dictMapper;

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
