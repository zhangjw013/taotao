package com.taotao.manage.service;

import com.taotao.manage.pojo.Item;

public interface ItemService extends BaseService<Item> {

    Long saveItem(Item item, String Dese);

    void updateItem(Item item, String Dese);

}
