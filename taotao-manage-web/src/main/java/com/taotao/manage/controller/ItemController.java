package com.taotao.manage.controller;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

/**
 * 商品
 */
@RequestMapping("/item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 保存商品
     *
     * @param item 商品基本信息
     * @param desc 商品描述信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam(value = "desc", required = false) String desc) {

        try {
            //保存商品信息和商品描述信息
            itemService.saveItem(item, desc);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateItem(Item item, @RequestParam(value = "desc", required = false) String desc) {

        try {
            itemService.updateItem(item, desc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void deleteItem(@RequestParam(value = "ids", required = false) Integer[] params) {

        try {
            itemService.deleteItem(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**
     * 根据商品标题分页查询商品
     *
     * @param title 商品标题
     * @param page  当前页号
     * @param rows  页大小
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DataGridResult> queryItemList(@RequestParam(value = "title", required = false) String title,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {

            DataGridResult dataGridResult = itemService.queryItemList(title, page, rows);
            return ResponseEntity.ok(dataGridResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
