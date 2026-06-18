package com.student.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.system.entity.Student;
import com.student.system.mapper.StudentMapper;
import com.student.system.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public List<Map<String, Object>> getStudentStatsByCollege() {
        return baseMapper.countByCollege();
    }

    @Override
    public List<Map<String, Object>> getStudentStatsByGender() {
        return baseMapper.countByGender();
    }

    @Override
    public List<Map<String, Object>> getStudentStatsByGrade() {
        return baseMapper.countByGrade();
    }
}
