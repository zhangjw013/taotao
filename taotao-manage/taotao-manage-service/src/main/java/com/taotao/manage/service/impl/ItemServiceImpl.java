package com.taotao.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    /**
     * 保存商品信息
     *
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
     *
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

    /**
     * 更改商品状态  删除
     *
     * @param ids
     */
    @Override
    public void deleteItem(Serializable[] ids) {
//        deleteByIds(ids);
//
//        ItemDesc itemDesc = new ItemDesc();
//        for (Serializable id : ids){
//            itemDesc.setItemId(Long.parseLong(id.toString()));
//            itemDescMapper.deleteByPrimaryKey(itemDesc);
//        }
        //更新商品状态为删除
        Item item = new Item();
        for (Serializable id : ids) {
            item.setId(Long.parseLong(id.toString()));
            item.setStatus(3);
            item.setUpdated(new Date());
            updateSelective(item);
        }

    }

    /**
     * 更改商品状态   下架
     *
     * @param ids
     */
    @Override
    public void instockItem(Serializable[] ids) {
        //更新商品状态为下架
        Item item = new Item();
        for (Serializable id : ids) {
            item.setId(Long.parseLong(id.toString()));
            item.setStatus(2);
            item.setUpdated(new Date());
            updateSelective(item);
        }
    }

    /**
     * 更改商品状态   上架
     *
     * @param ids
     */
    @Override
    public void reshelfItem(Serializable[] ids) {
        //更新商品状态为上架
        Item item = new Item();
        for (Serializable id : ids) {
            item.setId(Long.parseLong(id.toString()));
            item.setStatus(1);
            item.setUpdated(new Date());
            updateSelective(item);
        }
    }


    @Override
    public DataGridResult queryItemList(String title, Integer page, Integer rows) {

        //创建Example
        Example example = new Example(Item.class);
        try {
            if (StringUtils.isNoneBlank(title)) {
                //添加查询条件
                Example.Criteria criteria = example.createCriteria();
                //解码
                title = URLDecoder.decode(title, "utf-8");
                criteria.andLike("title", "%" + title + "%");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据更新时间降序排序
        example.orderBy("updated").desc();
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        List<Item> list = itemMapper.selectByExample(example);
        //转换为分页信息对象
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        //返回DataGridResult
        return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
