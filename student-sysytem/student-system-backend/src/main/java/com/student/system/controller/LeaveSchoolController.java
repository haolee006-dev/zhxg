package com.student.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.system.common.PageResult;
import com.student.system.common.Result;
import com.student.system.entity.Graduation;
import com.student.system.entity.LeaveSchool;
import com.student.system.service.GraduationService;
import com.student.system.service.LeaveSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave-school")
public class LeaveSchoolController {

    @Autowired
    private LeaveSchoolService leaveSchoolService;

    @Autowired
    private GraduationService graduationService;

    // 离校管理
    @GetMapping("/list")
    public Result<PageResult<LeaveSchool>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {
        
        Page<LeaveSchool> page = new Page<>(current, size);
        QueryWrapper<LeaveSchool> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("name", keyword)
                    .or()
                    .like("student_no", keyword));
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<LeaveSchool> result = leaveSchoolService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @GetMapping("/{id}")
    public Result<LeaveSchool> getById(@PathVariable Long id) {
        LeaveSchool leaveSchool = leaveSchoolService.getById(id);
        return Result.success(leaveSchool);
    }

    @PostMapping
    public Result<String> save(@RequestBody LeaveSchool leaveSchool) {
        leaveSchoolService.save(leaveSchool);
        return Result.success("添加成功");
    }

    @PutMapping
    public Result<String> update(@RequestBody LeaveSchool leaveSchool) {
        leaveSchoolService.updateById(leaveSchool);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        leaveSchoolService.removeById(id);
        return Result.success("删除成功");
    }

    // 毕业生管理
    @GetMapping("/graduation/list")
    public Result<PageResult<Graduation>> graduationList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String graduationStatus) {
        
        Page<Graduation> page = new Page<>(current, size);
        QueryWrapper<Graduation> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("name", keyword)
                    .or()
                    .like("student_no", keyword));
        }
        if (graduationStatus != null && !graduationStatus.isEmpty()) {
            wrapper.eq("graduation_status", graduationStatus);
        }
        
        wrapper.orderByDesc("create_time");
        Page<Graduation> result = graduationService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @GetMapping("/graduation/{id}")
    public Result<Graduation> getGraduationById(@PathVariable Long id) {
        Graduation graduation = graduationService.getById(id);
        return Result.success(graduation);
    }

    @PostMapping("/graduation")
    public Result<String> saveGraduation(@RequestBody Graduation graduation) {
        graduationService.save(graduation);
        return Result.success("添加成功");
    }

    @PutMapping("/graduation")
    public Result<String> updateGraduation(@RequestBody Graduation graduation) {
        graduationService.updateById(graduation);
        return Result.success("更新成功");
    }

    @DeleteMapping("/graduation/{id}")
    public Result<String> deleteGraduation(@PathVariable Long id) {
        graduationService.removeById(id);
        return Result.success("删除成功");
    }
}
