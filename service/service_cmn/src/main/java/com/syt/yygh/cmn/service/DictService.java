package com.syt.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import syt.hospital.model.cmn.Dict;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: foofoo3
 */
public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);

    void exportDict(HttpServletResponse response);

    void importDict(MultipartFile file);
}
