package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: QueryCourseParamsDto
 * @Package: com.xuecheng.model.dto
 * @Description:
 * @Datetime: 2023/2/15   21:29
 * @Author: YuHan.Kang@outlook.com
 */
@Data
@ToString
public class QueryCourseParamsDto {
    private String auditStatus;
    private String courseName;
    private String publishStatus;
}


