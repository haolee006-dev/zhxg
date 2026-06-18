package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("holiday_stay")
public class HolidayStay {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String holidayName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String contactPhone;
    private String status;
    private String approver;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
