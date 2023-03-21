package com.syt.yygh.order.service;

import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface WeixinService {

    Map createNative(Long orderId);

    Map<String, String> queryPayStatus(Long orderId);

    Boolean refund(Long orderId);
}
