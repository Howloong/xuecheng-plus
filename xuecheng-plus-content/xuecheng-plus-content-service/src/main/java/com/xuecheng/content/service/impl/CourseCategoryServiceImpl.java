package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CourseCategoryServiceImpl
 * @Package: com.xuecheng.content.service.impl
 * @Description:
 * @Datetime: 2023/2/22   15:25
 * @Author: YuHan.Kang@outlook.com
 */
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> categoryTreeDto = courseCategoryMapper.selectTreeNodes(id);
        List<CourseCategoryTreeDto> courseCategoryTreeDto = new ArrayList<>();
        Map<String, CourseCategoryTreeDto> map = new HashMap<>();
        categoryTreeDto.stream().forEach(item -> {
            map.put(item.getId(), item);
            if (item.getParentid().equals(id)) {
                courseCategoryTreeDto.add(item);
            }
            //找到该结点的父结点
            String parentid = item.getParentid();
            //找到该结点的父结点对象
            CourseCategoryTreeDto parentNode = map.get(parentid);
            if(parentNode!=null){
                List childrenTreeNodes = parentNode.getChildrenTreeNodes();
                if(childrenTreeNodes == null){
                    parentNode.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //找到子结点，放到它的父结点的childrenTreeNodes属性中
                parentNode.getChildrenTreeNodes().add(item);
            }
        });
        return courseCategoryTreeDto;
    }
}
