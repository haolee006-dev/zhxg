package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.LeaveSchool;
import com.student.system.mapper.LeaveSchoolMapper;
import com.student.system.service.LeaveSchoolService;
import org.springframework.stereotype.Service;

@Service
public class LeaveSchoolServiceImpl extends ServiceImpl<LeaveSchoolMapper, LeaveSchool> implements LeaveSchoolService {
}
