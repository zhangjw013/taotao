package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/query/{page}")
    public ResponseEntity<List<ItemCat>> queryItemCatListByPage
            (@PathVariable("page") Integer page, @RequestParam(value = "rows", defaultValue = "10") Integer rows) {
        try {

            //调用服务层对象查询数据
            List<ItemCat> itemCats = itemCatService.queryItemCatListByPage(page, rows);
            return ResponseEntity.ok(itemCats);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
