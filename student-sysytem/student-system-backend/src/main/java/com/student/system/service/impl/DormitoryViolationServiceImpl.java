package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.DormitoryViolation;
import com.student.system.mapper.DormitoryViolationMapper;
import com.student.system.service.DormitoryViolationService;
import org.springframework.stereotype.Service;

@Service
public class DormitoryViolationServiceImpl extends ServiceImpl<DormitoryViolationMapper, DormitoryViolation> implements DormitoryViolationService {
}
