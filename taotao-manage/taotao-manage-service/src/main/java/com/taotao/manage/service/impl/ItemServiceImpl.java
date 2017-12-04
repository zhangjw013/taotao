package com.taotao.manage.service.impl;

import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    /**
     * 保存商品信息
     * @param item
     * @param Dese
     * @return
     */
    @Override
    public Long saveItem(Item item, String Dese) {

        //保存商品基本信息
        saveSelective(item);

        //保存商品详细信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setCreated(new Date());
        itemDesc.setItemDesc(Dese);
        itemDesc.setUpdated(item.getCreated());
        itemDescMapper.insertSelective(itemDesc);

        return item.getId();

    }

    /**
     * 更新商品信息
     * @param item
     * @param Dese
     */
    @Override
    public void updateItem(Item item, String Dese) {

        //更新商品基本信息
        updateSelective(item);

        //更新商品详细信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(Dese);
        itemDesc.setUpdated(new Date());
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);

    }
}
