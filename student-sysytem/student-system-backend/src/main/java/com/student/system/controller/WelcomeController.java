package com.student.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.system.common.PageResult;
import com.student.system.common.Result;
import com.student.system.entity.WelcomeRegistration;
import com.student.system.entity.WelcomeProcess;
import com.student.system.service.WelcomeRegistrationService;
import com.student.system.service.WelcomeProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private WelcomeRegistrationService welcomeRegistrationService;

    @Autowired
    private WelcomeProcessService welcomeProcessService;

    // 预报到登记
    @GetMapping("/registration/list")
    public Result<PageResult<WelcomeRegistration>> registrationList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {
        
        Page<WelcomeRegistration> page = new Page<>(current, size);
        QueryWrapper<WelcomeRegistration> wrapper = new QueryWrapper<>();
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
        Page<WelcomeRegistration> result = welcomeRegistrationService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/registration")
    public Result<String> saveRegistration(@RequestBody WelcomeRegistration registration) {
        welcomeRegistrationService.save(registration);
        return Result.success("登记成功");
    }

    @PutMapping("/registration")
    public Result<String> updateRegistration(@RequestBody WelcomeRegistration registration) {
        welcomeRegistrationService.updateById(registration);
        return Result.success("更新成功");
    }

    @DeleteMapping("/registration/{id}")
    public Result<String> deleteRegistration(@PathVariable Long id) {
        welcomeRegistrationService.removeById(id);
        return Result.success("删除成功");
    }

    // 报到流程
    @GetMapping("/process/list")
    public Result<PageResult<WelcomeProcess>> processList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String status) {
        
        Page<WelcomeProcess> page = new Page<>(current, size);
        QueryWrapper<WelcomeProcess> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<WelcomeProcess> result = welcomeProcessService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/process")
    public Result<String> saveProcess(@RequestBody WelcomeProcess process) {
        welcomeProcessService.save(process);
        return Result.success("添加成功");
    }

    @PutMapping("/process")
    public Result<String> updateProcess(@RequestBody WelcomeProcess process) {
        welcomeProcessService.updateById(process);
        return Result.success("更新成功");
    }

    @DeleteMapping("/process/{id}")
    public Result<String> deleteProcess(@PathVariable Long id) {
        welcomeProcessService.removeById(id);
        return Result.success("删除成功");
    }
}
