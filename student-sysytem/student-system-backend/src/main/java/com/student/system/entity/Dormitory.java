package com.student.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("dormitory")
public class Dormitory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String buildingNo;
    private String roomNo;
    private Integer floor;
    private Integer capacity;
    private Integer currentCount;
    private String roomType;
    private String status;
    private String buildingType;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
