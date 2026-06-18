package com.student.system.controller;

import com.student.system.common.Result;
import com.student.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class DataAnalysisController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {
        Map<String, Object> data = new HashMap<>();
        
        // 学生统计
        data.put("collegeStats", studentService.getStudentStatsByCollege());
        data.put("genderStats", studentService.getStudentStatsByGender());
        data.put("gradeStats", studentService.getStudentStatsByGrade());
        
        return Result.success(data);
    }

    @GetMapping("/student/college")
    public Result<List<Map<String, Object>>> studentByCollege() {
        return Result.success(studentService.getStudentStatsByCollege());
    }

    @GetMapping("/student/gender")
    public Result<List<Map<String, Object>>> studentByGender() {
        return Result.success(studentService.getStudentStatsByGender());
    }

    @GetMapping("/student/grade")
    public Result<List<Map<String, Object>>> studentByGrade() {
        return Result.success(studentService.getStudentStatsByGrade());
    }
}
