package com.syt.yygh.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.yygh.common.helper.HttpRequestHelper;
import com.syt.yygh.hosp.client.HospitalFeignClient;
import com.syt.yygh.order.mapper.PaymentMapper;
import com.syt.yygh.order.service.OrderService;
import com.syt.yygh.order.service.PaymentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import syt.hospital.enums.OrderStatusEnum;
import syt.hospital.enums.PaymentStatusEnum;
import syt.hospital.enums.PaymentTypeEnum;
import syt.hospital.model.order.OrderInfo;
import syt.hospital.model.order.PaymentInfo;
import syt.hospital.vo.order.SignInfoVo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: foofoo3
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentInfo> implements PaymentService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HospitalFeignClient hospitalFeignClient;

    @Override
    public void savePaymentInfo(OrderInfo order, Integer paymentType) {
        //根据订单id和支付类型，查询支付记录表是否存在相同订单
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",order.getId());
        wrapper.eq("payment_type",paymentType);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            return;
        }
        //添加记录
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(order.getId());
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setOutTradeNo(order.getOutTradeNo());
        paymentInfo.setPaymentStatus(PaymentStatusEnum.UNPAID.getStatus());
        String subject = new DateTime(order.getReserveDate()).toString("yyyy-MM-dd")+"|"+order.getHosname()+"|"+order.getDepname()+"|"+order.getTitle();
        paymentInfo.setSubject(subject);
        paymentInfo.setTotalAmount(order.getAmount());
        baseMapper.insert(paymentInfo);
    }

    //更新订单状态
    @Override
    public void paySuccess(String out_trade_no, Map<String, String> resultMap) {
        //1 根据订单编号得到支付记录
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no",out_trade_no);
        wrapper.eq("payment_type", PaymentTypeEnum.WEIXIN.getStatus());
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);

        //2 更新支付记录信息
        paymentInfo.setPaymentStatus(PaymentStatusEnum.PAID.getStatus());
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setTradeNo(resultMap.get("transaction_id"));
        paymentInfo.setCallbackContent(resultMap.toString());
        baseMapper.updateById(paymentInfo);

        //3 根据订单号得到订单信息
        //4 更新订单信息
        OrderInfo orderInfo = orderService.getById(paymentInfo.getOrderId());
        orderInfo.setOrderStatus(OrderStatusEnum.PAID.getStatus());
        orderService.updateById(orderInfo);

        //5 调用医院接口，更新订单支付信息
        SignInfoVo signInfoVo = hospitalFeignClient.getSignInfoVo(orderInfo.getHoscode());
        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("hoscode",orderInfo.getHoscode());
        reqMap.put("hosRecordId",orderInfo.getHosRecordId());
        reqMap.put("timestamp", HttpRequestHelper.getTimestamp());
        String sign = HttpRequestHelper.getSign(reqMap, signInfoVo.getSignKey());
        reqMap.put("sign", sign);
        JSONObject result = HttpRequestHelper.sendRequest(reqMap, signInfoVo.getApiUrl() + "/order/updatePayStatus");
    }

    @Override
    public PaymentInfo getPaymentInfo(Long orderId, Integer paymentType) {
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",orderId);
        wrapper.eq("payment_type",paymentType);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);
        return paymentInfo;
    }
}
