package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.WelcomeProcess;
import com.student.system.mapper.WelcomeProcessMapper;
import com.student.system.service.WelcomeProcessService;
import org.springframework.stereotype.Service;

@Service
public class WelcomeProcessServiceImpl extends ServiceImpl<WelcomeProcessMapper, WelcomeProcess> implements WelcomeProcessService {
}
