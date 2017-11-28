package com.taotao.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows) {

        //设置分页
        PageHelper.startPage(page,rows);

        List<ItemCat> list = itemCatMapper.selectAll();

        return list;
    }
}
