package com.student.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.system.common.PageResult;
import com.student.system.common.Result;
import com.student.system.entity.Student;
import com.student.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    public Result<PageResult<Student>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String college,
            @RequestParam(required = false) String major,
            @RequestParam(required = false) Integer grade) {
        
        Page<Student> page = new Page<>(current, size);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("name", keyword)
                    .or()
                    .like("student_no", keyword));
        }
        if (college != null && !college.isEmpty()) {
            wrapper.eq("college", college);
        }
        if (major != null && !major.isEmpty()) {
            wrapper.eq("major", major);
        }
        if (grade != null) {
            wrapper.eq("grade", grade);
        }
        
        wrapper.orderByDesc("create_time");
        Page<Student> result = studentService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(), 
                result.getCurrent(), result.getSize()));
    }

    @GetMapping("/{id}")
    public Result<Student> getById(@PathVariable Long id) {
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    @PostMapping
    public Result<String> save(@RequestBody Student student) {
        studentService.save(student);
        return Result.success("添加成功");
    }

    @PutMapping
    public Result<String> update(@RequestBody Student student) {
        studentService.updateById(student);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        studentService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/stats/college")
    public Result<List<Map<String, Object>>> statsByCollege() {
        return Result.success(studentService.getStudentStatsByCollege());
    }

    @GetMapping("/stats/gender")
    public Result<List<Map<String, Object>>> statsByGender() {
        return Result.success(studentService.getStudentStatsByGender());
    }

    @GetMapping("/stats/grade")
    public Result<List<Map<String, Object>>> statsByGrade() {
        return Result.success(studentService.getStudentStatsByGrade());
    }
}
