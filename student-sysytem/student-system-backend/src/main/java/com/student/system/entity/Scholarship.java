package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("scholarship")
public class Scholarship {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String scholarshipName;
    private String scholarshipType;
    private BigDecimal amount;
    private LocalDate awardDate;
    private String semester;
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
