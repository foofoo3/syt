package com.syt.yygh.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import syt.hospital.vo.order.OrderCountQueryVo;

import java.util.Map;

/**
 * @Author: foofoo3
 */

@FeignClient(value = "service-order")
@Repository
public interface OrderFeignClient {

    @PostMapping("/api/order/orderInfo/inner/getCountMap")
    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo);

}
