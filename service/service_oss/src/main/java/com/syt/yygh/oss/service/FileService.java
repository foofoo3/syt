package com.syt.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: foofoo3
 */
public interface FileService {
    //上传文件到阿里云oss
    String upload(MultipartFile file);
}
