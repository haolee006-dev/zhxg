package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("disciplinary_action")
public class DisciplinaryAction {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String type;
    private String reason;
    private String level;
    private LocalDate actionDate;
    private String result;
    private String status;
    private String approver;
    private LocalDate approveDate;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
