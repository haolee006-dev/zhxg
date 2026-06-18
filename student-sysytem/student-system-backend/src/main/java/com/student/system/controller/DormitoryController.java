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
@RequestMapping("/dormitory")
public class DormitoryController {

    @Autowired
    private DormitoryService dormitoryService;

    @Autowired
    private DormitoryCheckService dormitoryCheckService;

    @Autowired
    private DormitoryRepairService dormitoryRepairService;

    @Autowired
    private DormitoryViolationService dormitoryViolationService;

    @Autowired
    private HolidayStayService holidayStayService;

    // 宿舍信息
    @GetMapping("/list")
    public Result<PageResult<Dormitory>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String buildingNo,
            @RequestParam(required = false) String status) {
        
        Page<Dormitory> page = new Page<>(current, size);
        QueryWrapper<Dormitory> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (buildingNo != null && !buildingNo.isEmpty()) {
            wrapper.eq("building_no", buildingNo);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByAsc("building_no", "room_no");
        Page<Dormitory> result = dormitoryService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @GetMapping("/{id}")
    public Result<Dormitory> getById(@PathVariable Long id) {
        Dormitory dormitory = dormitoryService.getById(id);
        return Result.success(dormitory);
    }

    @PostMapping
    public Result<String> save(@RequestBody Dormitory dormitory) {
        dormitoryService.save(dormitory);
        return Result.success("添加成功");
    }

    @PutMapping
    public Result<String> update(@RequestBody Dormitory dormitory) {
        dormitoryService.updateById(dormitory);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        dormitoryService.removeById(id);
        return Result.success("删除成功");
    }

    // 卫生检查
    @GetMapping("/check/list")
    public Result<PageResult<DormitoryCheck>> checkList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String buildingNo,
            @RequestParam(required = false) String roomNo) {
        
        Page<DormitoryCheck> page = new Page<>(current, size);
        QueryWrapper<DormitoryCheck> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (buildingNo != null && !buildingNo.isEmpty()) {
            wrapper.eq("building_no", buildingNo);
        }
        if (roomNo != null && !roomNo.isEmpty()) {
            wrapper.eq("room_no", roomNo);
        }
        
        wrapper.orderByDesc("check_date");
        Page<DormitoryCheck> result = dormitoryCheckService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/check")
    public Result<String> saveCheck(@RequestBody DormitoryCheck check) {
        dormitoryCheckService.save(check);
        return Result.success("添加成功");
    }

    @PutMapping("/check")
    public Result<String> updateCheck(@RequestBody DormitoryCheck check) {
        dormitoryCheckService.updateById(check);
        return Result.success("更新成功");
    }

    @DeleteMapping("/check/{id}")
    public Result<String> deleteCheck(@PathVariable Long id) {
        dormitoryCheckService.removeById(id);
        return Result.success("删除成功");
    }

    // 宿舍报修
    @GetMapping("/repair/list")
    public Result<PageResult<DormitoryRepair>> repairList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String buildingNo,
            @RequestParam(required = false) String status) {
        
        Page<DormitoryRepair> page = new Page<>(current, size);
        QueryWrapper<DormitoryRepair> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (buildingNo != null && !buildingNo.isEmpty()) {
            wrapper.eq("building_no", buildingNo);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<DormitoryRepair> result = dormitoryRepairService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/repair")
    public Result<String> saveRepair(@RequestBody DormitoryRepair repair) {
        dormitoryRepairService.save(repair);
        return Result.success("添加成功");
    }

    @PutMapping("/repair")
    public Result<String> updateRepair(@RequestBody DormitoryRepair repair) {
        dormitoryRepairService.updateById(repair);
        return Result.success("更新成功");
    }

    @DeleteMapping("/repair/{id}")
    public Result<String> deleteRepair(@PathVariable Long id) {
        dormitoryRepairService.removeById(id);
        return Result.success("删除成功");
    }

    // 宿舍违纪
    @GetMapping("/violation/list")
    public Result<PageResult<DormitoryViolation>> violationList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String buildingNo,
            @RequestParam(required = false) String status) {
        
        Page<DormitoryViolation> page = new Page<>(current, size);
        QueryWrapper<DormitoryViolation> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (buildingNo != null && !buildingNo.isEmpty()) {
            wrapper.eq("building_no", buildingNo);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<DormitoryViolation> result = dormitoryViolationService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/violation")
    public Result<String> saveViolation(@RequestBody DormitoryViolation violation) {
        dormitoryViolationService.save(violation);
        return Result.success("添加成功");
    }

    @PutMapping("/violation")
    public Result<String> updateViolation(@RequestBody DormitoryViolation violation) {
        dormitoryViolationService.updateById(violation);
        return Result.success("更新成功");
    }

    @DeleteMapping("/violation/{id}")
    public Result<String> deleteViolation(@PathVariable Long id) {
        dormitoryViolationService.removeById(id);
        return Result.success("删除成功");
    }

    // 假期留校
    @GetMapping("/holiday-stay/list")
    public Result<PageResult<HolidayStay>> holidayStayList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String status) {
        
        Page<HolidayStay> page = new Page<>(current, size);
        QueryWrapper<HolidayStay> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        Page<HolidayStay> result = holidayStayService.page(page, wrapper);
        
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(),
                result.getCurrent(), result.getSize()));
    }

    @PostMapping("/holiday-stay")
    public Result<String> saveHolidayStay(@RequestBody HolidayStay holidayStay) {
        holidayStayService.save(holidayStay);
        return Result.success("添加成功");
    }

    @PutMapping("/holiday-stay")
    public Result<String> updateHolidayStay(@RequestBody HolidayStay holidayStay) {
        holidayStayService.updateById(holidayStay);
        return Result.success("更新成功");
    }

    @DeleteMapping("/holiday-stay/{id}")
    public Result<String> deleteHolidayStay(@PathVariable Long id) {
        holidayStayService.removeById(id);
        return Result.success("删除成功");
    }
}
