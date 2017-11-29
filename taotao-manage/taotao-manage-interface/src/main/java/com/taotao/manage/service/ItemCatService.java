package com.taotao.manage.service;

import com.taotao.manage.pojo.ItemCat;

import java.util.List;

public interface ItemCatService extends BaseService<ItemCat>{

    List<ItemCat> queryItemCatListByPage(Integer page, Integer rows);

}
