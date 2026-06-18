package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.DormitoryCheck;
import com.student.system.mapper.DormitoryCheckMapper;
import com.student.system.service.DormitoryCheckService;
import org.springframework.stereotype.Service;

@Service
public class DormitoryCheckServiceImpl extends ServiceImpl<DormitoryCheckMapper, DormitoryCheck> implements DormitoryCheckService {
}
