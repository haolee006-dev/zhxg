package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.WelcomeRegistration;
import com.student.system.mapper.WelcomeRegistrationMapper;
import com.student.system.service.WelcomeRegistrationService;
import org.springframework.stereotype.Service;

@Service
public class WelcomeRegistrationServiceImpl extends ServiceImpl<WelcomeRegistrationMapper, WelcomeRegistration> implements WelcomeRegistrationService {
}
