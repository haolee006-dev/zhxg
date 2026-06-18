package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("student")
public class Student {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String studentNo;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String idCard;
    private String phone;
    private String email;
    private String address;
    private String nativePlace;
    private String nation;
    private String politicalStatus;
    private String college;
    private String major;
    private String className;
    private Integer grade;
    private String educationLevel;
    private String status;
    private LocalDate enrollmentDate;
    private LocalDate expectedGraduation;
    private String dormitoryNo;
    private String roomNo;
    private String counselorId;
    private String photo;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
