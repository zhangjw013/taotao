package com.taotao.manage.service;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Item;

import java.io.Serializable;

public interface ItemService extends BaseService<Item> {

    Long saveItem(Item item, String Dese);

    void updateItem(Item item, String Dese);

    void deleteItem(Serializable[] ids);

    DataGridResult queryItemList(String title, Integer page, Integer rows);

}
