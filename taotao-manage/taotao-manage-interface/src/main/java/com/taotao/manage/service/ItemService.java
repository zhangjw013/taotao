package com.taotao.manage.service;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Item;

import java.io.Serializable;

public interface ItemService extends BaseService<Item> {

    Long saveItem(Item item, String Dese);

    void updateItem(Item item, String Dese);

    //更改删除状态
    void deleteItem(Serializable[] ids);

    //更改下架状态
    void instockItem(Serializable[] ids);

    //更改上架状态
    void reshelfItem(Serializable[] ids);

    DataGridResult queryItemList(String title, Integer page, Integer rows);

}
