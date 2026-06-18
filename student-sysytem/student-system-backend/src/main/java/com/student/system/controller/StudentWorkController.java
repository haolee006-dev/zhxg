package com.student.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.system.common.PageResult;
import com.student.system.common.Result;
import com.student.system.entity.*;
import com.student.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student-work")
public class StudentWorkController {

    @Autowired
    private StudentCardService studentCardService;

    @Autowired
    private ScholarshipService scholarshipService;

    @Autowired
    private FinancialAidService financialAidService;

    @Autowired
    private StudentEvaluationService studentEvaluationService;

    @Autowired
    private HealthRecordService healthRecordService;

    @Autowired
    private StudentNotificationService studentNotificationService;

    @Autowired
    private DisciplinaryActionService disciplinaryActionService;

    // 学生证办理
    @GetMapping("/card/list")
    public Result<PageResult<StudentCard>> cardList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String status) {
        
        Page<StudentCard> page = new Page<>(current, size);
        QueryWrapper<StudentCard> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<StudentCard> result = studentCardService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/card")
    public Result<String> saveCard(@RequestBody StudentCard card) {
        studentCardService.save(card);
        return Result.success("添加成功");
    }

    @PutMapping("/card")
    public Result<String> updateCard(@RequestBody StudentCard card) {
        studentCardService.updateById(card);
        return Result.success("更新成功");
    }

    @DeleteMapping("/card/{id}")
    public Result<String> deleteCard(@PathVariable Long id) {
        studentCardService.removeById(id);
        return Result.success("删除成功");
    }

    // 奖学金管理
    @GetMapping("/scholarship/list")
    public Result<PageResult<Scholarship>> scholarshipList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String status) {
        
        Page<Scholarship> page = new Page<>(current, size);
        QueryWrapper<Scholarship> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<Scholarship> result = scholarshipService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/scholarship")
    public Result<String> saveScholarship(@RequestBody Scholarship scholarship) {
        scholarshipService.save(scholarship);
        return Result.success("添加成功");
    }

    @PutMapping("/scholarship")
    public Result<String> updateScholarship(@RequestBody Scholarship scholarship) {
        scholarshipService.updateById(scholarship);
        return Result.success("更新成功");
    }

    @DeleteMapping("/scholarship/{id}")
    public Result<String> deleteScholarship(@PathVariable Long id) {
        scholarshipService.removeById(id);
        return Result.success("删除成功");
    }

    // 资助管理
    @GetMapping("/financial-aid/list")
    public Result<PageResult<FinancialAid>> financialAidList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String status) {
        
        Page<FinancialAid> page = new Page<>(current, size);
        QueryWrapper<FinancialAid> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<FinancialAid> result = financialAidService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/financial-aid")
    public Result<String> saveFinancialAid(@RequestBody FinancialAid financialAid) {
        financialAidService.save(financialAid);
        return Result.success("添加成功");
    }

    @PutMapping("/financial-aid")
    public Result<String> updateFinancialAid(@RequestBody FinancialAid financialAid) {
        financialAidService.updateById(financialAid);
        return Result.success("更新成功");
    }

    @DeleteMapping("/financial-aid/{id}")
    public Result<String> deleteFinancialAid(@PathVariable Long id) {
        financialAidService.removeById(id);
        return Result.success("删除成功");
    }

    // 学情测评
    @GetMapping("/evaluation/list")
    public Result<PageResult<StudentEvaluation>> evaluationList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String semester) {
        
        Page<StudentEvaluation> page = new Page<>(current, size);
        QueryWrapper<StudentEvaluation> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        if (semester != null && !semester.isEmpty()) {
            wrapper.eq("semester", semester);
        }
        
        wrapper.orderByDesc("create_time");
        Page<StudentEvaluation> result = studentEvaluationService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/evaluation")
    public Result<String> saveEvaluation(@RequestBody StudentEvaluation evaluation) {
        studentEvaluationService.save(evaluation);
        return Result.success("添加成功");
    }

    @PutMapping("/evaluation")
    public Result<String> updateEvaluation(@RequestBody StudentEvaluation evaluation) {
        studentEvaluationService.updateById(evaluation);
        return Result.success("更新成功");
    }

    @DeleteMapping("/evaluation/{id}")
    public Result<String> deleteEvaluation(@PathVariable Long id) {
        studentEvaluationService.removeById(id);
        return Result.success("删除成功");
    }

    // 健康档案
    @GetMapping("/health/list")
    public Result<PageResult<HealthRecord>> healthList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId) {
        
        Page<HealthRecord> page = new Page<>(current, size);
        QueryWrapper<HealthRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        
        wrapper.orderByDesc("create_time");
        Page<HealthRecord> result = healthRecordService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/health")
    public Result<String> saveHealth(@RequestBody HealthRecord healthRecord) {
        healthRecordService.save(healthRecord);
        return Result.success("添加成功");
    }

    @PutMapping("/health")
    public Result<String> updateHealth(@RequestBody HealthRecord healthRecord) {
        healthRecordService.updateById(healthRecord);
        return Result.success("更新成功");
    }

    @DeleteMapping("/health/{id}")
    public Result<String> deleteHealth(@PathVariable Long id) {
        healthRecordService.removeById(id);
        return Result.success("删除成功");
    }

    // 学生通报
    @GetMapping("/notification/list")
    public Result<PageResult<StudentNotification>> notificationList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String receiverType) {
        
        Page<StudentNotification> page = new Page<>(current, size);
        QueryWrapper<StudentNotification> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (type != null && !type.isEmpty()) {
            wrapper.eq("type", type);
        }
        if (receiverType != null && !receiverType.isEmpty()) {
            wrapper.eq("receiver_type", receiverType);
        }
        
        wrapper.orderByDesc("create_time");
        Page<StudentNotification> result = studentNotificationService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/notification")
    public Result<String> saveNotification(@RequestBody StudentNotification notification) {
        studentNotificationService.save(notification);
        return Result.success("添加成功");
    }

    @PutMapping("/notification")
    public Result<String> updateNotification(@RequestBody StudentNotification notification) {
        studentNotificationService.updateById(notification);
        return Result.success("更新成功");
    }

    @DeleteMapping("/notification/{id}")
    public Result<String> deleteNotification(@PathVariable Long id) {
        studentNotificationService.removeById(id);
        return Result.success("删除成功");
    }

    // 违纪处分
    @GetMapping("/disciplinary/list")
    public Result<PageResult<DisciplinaryAction>> disciplinaryList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String status) {
        
        Page<DisciplinaryAction> page = new Page<>(current, size);
        QueryWrapper<DisciplinaryAction> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<DisciplinaryAction> result = disciplinaryActionService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/disciplinary")
    public Result<String> saveDisciplinary(@RequestBody DisciplinaryAction disciplinaryAction) {
        disciplinaryActionService.save(disciplinaryAction);
        return Result.success("添加成功");
    }

    @PutMapping("/disciplinary")
    public Result<String> updateDisciplinary(@RequestBody DisciplinaryAction disciplinaryAction) {
        disciplinaryActionService.updateById(disciplinaryAction);
        return Result.success("更新成功");
    }

    @DeleteMapping("/disciplinary/{id}")
    public Result<String> deleteDisciplinary(@PathVariable Long id) {
        disciplinaryActionService.removeById(id);
        return Result.success("删除成功");
    }
}
