package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.Counselor;
import com.student.system.mapper.CounselorMapper;
import com.student.system.service.CounselorService;
import org.springframework.stereotype.Service;

@Service
public class CounselorServiceImpl extends ServiceImpl<CounselorMapper, Counselor> implements CounselorService {
}
