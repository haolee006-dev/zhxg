package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.Graduation;
import com.student.system.mapper.GraduationMapper;
import com.student.system.service.GraduationService;
import org.springframework.stereotype.Service;

@Service
public class GraduationServiceImpl extends ServiceImpl<GraduationMapper, Graduation> implements GraduationService {
}
