package com.syt.yygh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import syt.hospital.model.order.OrderInfo;
import syt.hospital.vo.order.OrderCountQueryVo;
import syt.hospital.vo.order.OrderCountVo;

import java.util.List;

/**
 * @Author: foofoo3
 */
public interface OrderMapper extends BaseMapper<OrderInfo> {
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
