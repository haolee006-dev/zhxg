package com.student.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.system.common.PageResult;
import com.student.system.common.Result;
import com.student.system.entity.Counselor;
import com.student.system.service.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/counselor")
public class CounselorController {

    @Autowired
    private CounselorService counselorService;

    @GetMapping("/list")
    public Result<PageResult<Counselor>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String college) {
        
        Page<Counselor> page = new Page<>(current, size);
        QueryWrapper<Counselor> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("name", keyword)
                    .or()
                    .like("employee_no", keyword));
        }
        if (college != null && !college.isEmpty()) {
            wrapper.eq("college", college);
        }
        
        wrapper.orderByDesc("create_time");
        Page<Counselor> result = counselorService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @GetMapping("/{id}")
    public Result<Counselor> getById(@PathVariable Long id) {
        Counselor counselor = counselorService.getById(id);
        return Result.success(counselor);
    }

    @PostMapping
    public Result<String> save(@RequestBody Counselor counselor) {
        counselorService.save(counselor);
        return Result.success("添加成功");
    }

    @PutMapping
    public Result<String> update(@RequestBody Counselor counselor) {
        counselorService.updateById(counselor);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        counselorService.removeById(id);
        return Result.success("删除成功");
    }
}
