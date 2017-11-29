package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 分页查询类目列表
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/query/{page}")
    public ResponseEntity<List<ItemCat>> queryItemCatListByPage
    (@PathVariable("page") Integer page, @RequestParam(value = "rows", defaultValue = "0") Integer rows) {
        try {

            //调用服务层对象查询数据
//            List<ItemCat> itemCats = itemCatService.queryItemCatListByPage(page, rows);
            List<ItemCat> itemCats = itemCatService.queryListByPage(page, rows);
            return ResponseEntity.ok(itemCats);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * url:'/rest/item/cat',
     * method:'GET',
     * 根据父类目id查询子类目
     *
     * @param parentId 父类目id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId
    (@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        try {
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> itemCats = itemCatService.queryListByWhere(itemCat);
            return ResponseEntity.ok(itemCats);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
