package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.StudentEvaluation;
import com.student.system.mapper.StudentEvaluationMapper;
import com.student.system.service.StudentEvaluationService;
import org.springframework.stereotype.Service;

@Service
public class StudentEvaluationServiceImpl extends ServiceImpl<StudentEvaluationMapper, StudentEvaluation> implements StudentEvaluationService {
}
