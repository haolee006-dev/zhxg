package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("financial_aid")
public class FinancialAid {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String aidType;
    private String aidName;
    private BigDecimal amount;
    private LocalDate applyDate;
    private LocalDate approveDate;
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
