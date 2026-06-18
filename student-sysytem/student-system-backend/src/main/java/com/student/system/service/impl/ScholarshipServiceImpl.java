package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.Scholarship;
import com.student.system.mapper.ScholarshipMapper;
import com.student.system.service.ScholarshipService;
import org.springframework.stereotype.Service;

@Service
public class ScholarshipServiceImpl extends ServiceImpl<ScholarshipMapper, Scholarship> implements ScholarshipService {
}
