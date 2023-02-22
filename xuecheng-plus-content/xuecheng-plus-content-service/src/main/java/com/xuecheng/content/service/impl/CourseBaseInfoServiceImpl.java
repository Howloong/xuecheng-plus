package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.content.base.model.PageParams;
import com.xuecheng.content.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: CourseBaseInfoServiceImpl
 * @Package: com.xuecheng.content.service.impl
 * @Description:
 * @Datetime: 2023/2/16   15:59
 * @Author: YuHan.Kang@outlook.com
 */

@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageparams, QueryCourseParamsDto paramsDto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(paramsDto.getCourseName()), CourseBase::getName, paramsDto.getCourseName());
        queryWrapper.eq(StringUtils.isNotEmpty(paramsDto.getAuditStatus()), CourseBase::getAuditStatus, paramsDto.getAuditStatus());
        queryWrapper.eq(StringUtils.isNotEmpty(paramsDto.getPublishStatus()), CourseBase::getStatus, paramsDto.getPublishStatus());

        Page<CourseBase> courseBasePage = new Page<>(pageparams.getPageNo(), pageparams.getPageSize());
        courseBaseMapper.selectPage(courseBasePage, queryWrapper);
        List<CourseBase> records = courseBasePage.getRecords();
        long total = courseBasePage.getTotal();
        return new PageResult<>(records, total, pageparams.getPageNo(), pageparams.getPageSize());
    }
}
