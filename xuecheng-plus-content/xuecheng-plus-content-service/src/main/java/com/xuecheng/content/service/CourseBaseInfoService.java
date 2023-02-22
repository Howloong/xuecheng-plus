package com.xuecheng.content.service;

import com.xuecheng.content.base.model.PageParams;
import com.xuecheng.content.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @ClassName: CourseBaseInfoService
 * @Package: com.xuecheng.content.service
 * @Description:
 * @Datetime: 2023/2/16   15:56
 * @Author: YuHan.Kang@outlook.com
 */
public interface CourseBaseInfoService {
   PageResult<CourseBase> queryCourseBaseList(PageParams pageparams, QueryCourseParamsDto paramsDto);
}
