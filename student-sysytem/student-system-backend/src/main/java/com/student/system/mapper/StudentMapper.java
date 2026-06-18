package com.student.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.system.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("SELECT college as name, COUNT(*) as value FROM student WHERE deleted = 0 GROUP BY college")
    List<Map<String, Object>> countByCollege();

    @Select("SELECT gender as name, COUNT(*) as value FROM student WHERE deleted = 0 GROUP BY gender")
    List<Map<String, Object>> countByGender();

    @Select("SELECT grade as name, COUNT(*) as value FROM student WHERE deleted = 0 GROUP BY grade")
    List<Map<String, Object>> countByGrade();
}
