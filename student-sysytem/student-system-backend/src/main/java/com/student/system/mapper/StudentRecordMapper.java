package com.student.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.system.entity.StudentRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentRecordMapper extends BaseMapper<StudentRecord> {
}
