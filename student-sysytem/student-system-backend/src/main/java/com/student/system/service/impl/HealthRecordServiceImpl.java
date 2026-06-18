package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.HealthRecord;
import com.student.system.mapper.HealthRecordMapper;
import com.student.system.service.HealthRecordService;
import org.springframework.stereotype.Service;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecord> implements HealthRecordService {
}
