package com.syt.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: foofoo3
 */

@FeignClient("service-cmn")
@Repository
public interface DictFeignClient {

        @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
        String getName(@PathVariable("dictCode") String dictCode,
                              @PathVariable("value") String value);

        @GetMapping("/admin/cmn/dict/getName/{value}")
        String getName(@PathVariable("value") String value);
}
