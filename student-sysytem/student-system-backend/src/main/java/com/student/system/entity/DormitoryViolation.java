package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("dormitory_violation")
public class DormitoryViolation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long dormitoryId;
    private String buildingNo;
    private String roomNo;
    private String violationType;
    private String description;
    private LocalDate violationDate;
    private String punishment;
    private String status;
    private String handler;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
