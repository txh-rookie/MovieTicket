package com.serookie.movie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.Area;
import com.serookie.movie.mapper.AreaMapper;
import com.serookie.movie.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2021/12/28
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Resource
    private AreaMapper areaMapper;

    @Override
    public List<Area> getArea() {
        List<Area> all = areaMapper.selectList(null);
        List<Area> areaList = all.stream().filter(area -> {
            return area.getParentCid().equals(0);
        }).map(menu -> {
            menu.setAreaList(allTree(menu,all));
            return menu;
        }).collect(Collectors.toList());
        return areaList;
    }
    private List<Area> allTree(Area root,List<Area> all){
        List<Area> categories = all.stream().filter(Area-> {
            //草了 这个equals的比较不能比较Integer和Long的类型
            int areaId = root.getAreaId().intValue();
            return Area.getParentCid().equals(areaId);
        }).map(Area->{
            Area.setAreaList(allTree(Area,all));
            return Area;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return categories;
    }
}
