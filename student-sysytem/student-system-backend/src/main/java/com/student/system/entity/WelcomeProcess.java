package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("welcome_process")
public class WelcomeProcess {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String processName;
    private String processType;
    private String status;
    private LocalDateTime completeTime;
    private String operator;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
