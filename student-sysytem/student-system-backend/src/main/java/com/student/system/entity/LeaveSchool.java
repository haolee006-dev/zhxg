package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("leave_school")
public class LeaveSchool {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private String studentNo;
    private String name;
    private String leaveType;
    private LocalDate expectedLeaveDate;
    private LocalDate actualLeaveDate;
    private String destination;
    private String contactPhone;
    private String reason;
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
