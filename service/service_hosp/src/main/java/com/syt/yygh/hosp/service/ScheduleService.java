package com.syt.yygh.hosp.service;

import org.springframework.data.domain.Page;
import syt.hospital.model.hosp.Department;
import syt.hospital.model.hosp.Schedule;
import syt.hospital.vo.hosp.ScheduleQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: foofoo3
 */
public interface ScheduleService {
    void save(Map<String, Object> paramMap);

    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    void remove(String hoscode, String hosScheduleId);

    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);
}
