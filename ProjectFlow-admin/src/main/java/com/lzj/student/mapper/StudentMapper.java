package com.lzj.student.mapper;

import com.lzj.student.domain.Student;

import java.util.List;

/**
 * 学生信息Mapper接口
 *
 * @author ruoyi
 * @date 2024-06-09
 */
public interface StudentMapper
{
    /**
     * 查询学生信息
     *
     * @param id 学生信息主键
     * @return 学生信息
     */
    public Student selectStudentById(Long id);

    /**
     * 查询学生信息列表
     *
     * @param student 学生信息
     * @return 学生信息集合
     */
    public List<Student> selectStudentList(Student student);

    /**
     * 新增学生信息
     *
     * @param student 学生信息
     * @return 结果
     */
    public int insertStudent(Student student);

    /**
     * 修改学生信息
     *
     * @param student 学生信息
     * @return 结果
     */
    public int updateStudent(Student student);

    /**
     * 删除学生信息
     *
     * @param id 学生信息主键
     * @return 结果
     */
    public int deleteStudentById(Long id);

    /**
     * 批量删除学生信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentByIds(Long[] ids);
}
