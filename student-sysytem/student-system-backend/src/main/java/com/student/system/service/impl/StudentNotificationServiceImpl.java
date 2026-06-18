package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.StudentNotification;
import com.student.system.mapper.StudentNotificationMapper;
import com.student.system.service.StudentNotificationService;
import org.springframework.stereotype.Service;

@Service
public class StudentNotificationServiceImpl extends ServiceImpl<StudentNotificationMapper, StudentNotification> implements StudentNotificationService {
}
