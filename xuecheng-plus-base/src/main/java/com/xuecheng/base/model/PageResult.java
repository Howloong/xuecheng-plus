package com.xuecheng.base.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName: PageResult
 * @Package: com.xuecheng.base.model
 * @Description:
 * @Datetime: 2023/2/16   09:48
 * @Author: YuHan.Kang@outlook.com
 */
@Data
@ToString
public class PageResult<T> {
    // 数据列表
    private List<T> items;

    //总记录数
    private long counts;

    //当前页码
    private long page;

    //每页记录数
    private long pageSize;

    public PageResult(List<T> items, long counts, long page, long pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }

}