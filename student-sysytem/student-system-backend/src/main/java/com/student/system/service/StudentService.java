package com.student.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.system.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService extends IService<Student> {
    List<Map<String, Object>> getStudentStatsByCollege();
    List<Map<String, Object>> getStudentStatsByGender();
    List<Map<String, Object>> getStudentStatsByGrade();
}
