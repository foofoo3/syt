package com.syt.yygh.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syt.hospital.model.order.PaymentInfo;
import syt.hospital.model.order.RefundInfo;

/**
 * @Author: foofoo3
 */
public interface RefundInfoService extends IService<RefundInfo> {

    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);

}
