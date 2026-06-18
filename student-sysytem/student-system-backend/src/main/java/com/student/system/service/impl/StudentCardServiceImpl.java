package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.StudentCard;
import com.student.system.mapper.StudentCardMapper;
import com.student.system.service.StudentCardService;
import org.springframework.stereotype.Service;

@Service
public class StudentCardServiceImpl extends ServiceImpl<StudentCardMapper, StudentCard> implements StudentCardService {
}
