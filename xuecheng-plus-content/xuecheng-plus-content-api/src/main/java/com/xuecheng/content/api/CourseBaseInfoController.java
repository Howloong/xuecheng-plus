package com.xuecheng.content.api;

import com.xuecheng.content.base.model.PageParams;
import com.xuecheng.content.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: CourseBaseInfoController
 * @Package: com.xuecheng.api
 * @Description:
 * @Datetime: 2023/2/16   09:56
 * @Author: YuHan.Kang@outlook.com
 */
@Api(value = "课程管理接口", tags = "课程管理接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @PostMapping("/course/list")
    @ApiOperation("课程查询接口")
    public PageResult<CourseBase> list(PageParams pageparams, @RequestBody QueryCourseParamsDto paramsDto) {
        return courseBaseInfoService.queryCourseBaseList(pageparams, paramsDto);
    }
}
