package com.syt.yygh.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syt.hospital.model.order.OrderInfo;
import syt.hospital.model.order.PaymentInfo;

import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface PaymentService extends IService<PaymentInfo> {
    void savePaymentInfo(OrderInfo order, Integer status);

    void paySuccess(String out_trade_no, Map<String, String> resultMap);

    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);
}
