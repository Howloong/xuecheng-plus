package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper categoryMapper  ;

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

    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        //对参数进行合法性的校验
        //合法性校验
        if (StringUtils.isBlank(dto.getName())) {
            throw new RuntimeException("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }
        CourseBase courseBase = new CourseBase();
        //将传入dto的数据设置到 courseBase对象
//        courseBase.setName(dto.getName());
//        courseBase.setMt(dto.getMt());
//        courseBase.setSt(dto.getSt());
        //将dto中和courseBase属性名一样的属性值拷贝到courseBase
        BeanUtils.copyProperties(dto, courseBase);
        //设置机构id
        courseBase.setCompanyId(companyId);
        //创建时间
        courseBase.setCreateDate(LocalDateTime.now());
        //审核状态默认为未提交
        courseBase.setAuditStatus("202002");
        //发布状态默认为未发布
        courseBase.setStatus("203001");
        //课程基本表插入一条记录
        int insert = courseBaseMapper.insert(courseBase);
        Long courseId = courseBase.getId();
        //获取课程id
        CourseMarket courseMarket = new CourseMarket();
        //将dto中和courseMarket属性名一样的属性值拷贝到courseMarket
        BeanUtils.copyProperties(dto, courseMarket);
        courseMarket.setId(courseId);
        if (dto.getCharge().equals("201001")) {
            if (courseMarket.getPrice() == null|| courseMarket.getPrice() <=0) {
                throw new RuntimeException("课程为收费但是价格为空");
            }
        }
        int insert1 = courseMarketMapper.insert(courseMarket);
        if (insert <= 0 || insert1 <= 0) {
            throw new RuntimeException("添加课程失败");
        }
        return getCourseBaseInfo(courseId);
    }

    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        String mt = courseBase.getMt();
        String st = courseBase.getSt();
        CourseCategory mtCategory = categoryMapper.selectById(mt);
        CourseCategory stCategory = categoryMapper.selectById(st);
        if (mtCategory != null) {
            String mtName = mtCategory.getName();
            courseBaseInfoDto.setMtName(mtName);
        }
        if (stCategory != null) {
            String stName = stCategory.getName();
            courseBaseInfoDto.setMtName(stName);
        }
        return courseBaseInfoDto;
    }
}
