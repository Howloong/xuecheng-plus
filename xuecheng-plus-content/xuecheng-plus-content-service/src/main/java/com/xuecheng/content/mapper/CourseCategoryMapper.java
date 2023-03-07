package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;

import java.util.List;

/**
 * @ClassName: CourseCategoryMapper
 * @Package: com.xuecheng.content.mapper
 * @Description:
 * @Datetime: 2023/2/22   15:19
 * @Author: YuHan.Kang@outlook.com
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {
    List<CourseCategoryTreeDto> selectTreeNodes(String id);

}
