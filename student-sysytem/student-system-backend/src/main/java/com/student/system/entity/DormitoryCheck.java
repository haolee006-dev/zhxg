package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("dormitory_check")
public class DormitoryCheck {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long dormitoryId;
    private String buildingNo;
    private String roomNo;
    private LocalDate checkDate;
    private String checkType;
    private Integer score;
    private String result;
    private String checker;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
