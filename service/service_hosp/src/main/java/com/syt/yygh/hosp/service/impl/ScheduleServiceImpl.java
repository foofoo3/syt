package com.syt.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.syt.yygh.hosp.repository.ScheduleRepository;
import com.syt.yygh.hosp.service.DepartmentService;
import com.syt.yygh.hosp.service.HospitalService;
import com.syt.yygh.hosp.service.ScheduleService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import syt.hospital.model.hosp.Schedule;
import syt.hospital.vo.hosp.BookingScheduleRuleVo;
import syt.hospital.vo.hosp.ScheduleQueryVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: foofoo3
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public void save(Map<String, Object> paramMap) {
        String mapString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(mapString, Schedule.class);

        Schedule getSchedule = scheduleRepository
                .getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());

        if (getSchedule != null) {
            schedule.setCreateTime(getSchedule.getCreateTime());

        }else {
            schedule.setCreateTime(new Date());
        }
        schedule.setUpdateTime(new Date());
        schedule.setIsDeleted(0);
        schedule.setStatus(1);
        scheduleRepository.save(schedule);
    }

    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        Pageable pageable = PageRequest.of(page-1,limit);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo,schedule);
        schedule.setIsDeleted(0);
        schedule.setStatus(1);

        Example<Schedule> example = Example.of(schedule,matcher);

        Page<Schedule> all = scheduleRepository.findAll(example, pageable);

        return all;
    }

    @Override
    public void remove(String hoscode, String hosScheduleId) {
        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if (schedule != null) {
            scheduleRepository.deleteById(schedule.getId());
        }
    }

    @Override
    public Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode) {
        //1 ?????????????????? ??? ???????????? ??????
        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);

        //2 ???????????????workDate???????????????
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),//????????????
                Aggregation.group("workDate")//????????????
                        .first("workDate").as("workDate")
                        //3 ??????????????????
                        .count().as("docCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                //??????
                Aggregation.sort(Sort.Direction.DESC,"workDate"),
                //4 ????????????
                Aggregation.skip((page-1)*limit),
                Aggregation.limit(limit)
        );
        //???????????????????????????
        AggregationResults<BookingScheduleRuleVo> aggResults =
                mongoTemplate.aggregate(agg, Schedule.class, BookingScheduleRuleVo.class);
        List<BookingScheduleRuleVo> bookingScheduleRuleVoList = aggResults.getMappedResults();

        //???????????????????????????
        Aggregation totalAgg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );
        AggregationResults<BookingScheduleRuleVo> totalAggResults =
                mongoTemplate.aggregate(totalAgg, Schedule.class, BookingScheduleRuleVo.class);
        int total = totalAggResults.getMappedResults().size();

        //???????????????????????????
        for(BookingScheduleRuleVo bookingScheduleRuleVo:bookingScheduleRuleVoList) {
            Date workDate = bookingScheduleRuleVo.getWorkDate();
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);
        }

        //?????????????????????????????????
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList",bookingScheduleRuleVoList);
        result.put("total",total);

        //??????????????????
        String hosName = hospitalService.getHospName(hoscode);
        //??????????????????
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("hosname",hosName);

        result.put("baseMap",baseMap);

        return result;
    }

    @Override//
     public List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate) {
        //??????????????????mongodb
        List<Schedule> scheduleList =
                scheduleRepository.findScheduleByHoscodeAndDepcodeAndWorkDate(hoscode,depcode,new DateTime(workDate).toDate());
        //?????????list????????????????????????????????????????????????????????????????????????????????????
        scheduleList.stream().forEach(item->{
            this.packageSchedule(item);
        });
        return scheduleList;
    }

    private Schedule packageSchedule(Schedule schedule) {
        //??????????????????
        schedule.getParam().put("hosname",hospitalService.getHospName(schedule.getHoscode()));
        //??????????????????
        schedule.getParam().put("depname",departmentService.getDepName(schedule.getHoscode(),schedule.getDepcode()));
        //????????????????????????
        schedule.getParam().put("dayOfWeek",this.getDayOfWeek(new DateTime(schedule.getWorkDate())));
        return schedule;
    }

    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "??????";
            default:
                break;
        }
        return dayOfWeek;
    }
}
