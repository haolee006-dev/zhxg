package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.HolidayStay;
import com.student.system.mapper.HolidayStayMapper;
import com.student.system.service.HolidayStayService;
import org.springframework.stereotype.Service;

@Service
public class HolidayStayServiceImpl extends ServiceImpl<HolidayStayMapper, HolidayStay> implements HolidayStayService {
}
