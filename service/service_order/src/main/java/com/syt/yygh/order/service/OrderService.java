package com.syt.yygh.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import syt.hospital.model.order.OrderInfo;
import syt.hospital.vo.order.OrderCountQueryVo;
import syt.hospital.vo.order.OrderQueryVo;

import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface OrderService extends IService<OrderInfo> {
    Long saveOrder(String scheduleId, Long patientId);

    OrderInfo getOrder(String orderId);

    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    Map<String,Object> show(Long orderId);

    Boolean cancelOrder(Long orderId);

    void patientTips();

    Map<String,Object> getCountMap(OrderCountQueryVo orderCountQueryVo );
}
