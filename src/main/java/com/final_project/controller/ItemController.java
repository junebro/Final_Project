package com.final_project.controller;

import com.final_project.entity.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    // 단일 아이템 조회를 위한 경로 설정
    @GetMapping("/api/item")
    public Item getItem() {
        Item item = new Item();
        item.setId("abcd");
        item.setName("가나다라");
        item.setDescription("12345667878");
        return item;
    }
}