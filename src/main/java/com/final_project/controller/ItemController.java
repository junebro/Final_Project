package com.final_project.controller;

import com.final_project.dto.InputData;
import com.final_project.entity.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    // 단일 아이템 조회를 위한 경로 설정
    @GetMapping("/api/item")
    public Item getItem() {
        Item item = new Item();
        item.setId("abcd");
        item.setName("가나다라마");
        item.setDescription("12345667878");
        return item;
    }

    // 사용자 입력을 받는 POST 요청 메서드 추가
//    @PostMapping("/api/item")
//    public ResponseEntity<String> receiveItemData(@RequestBody Item item) {
//        // 여기서 입력받은 데이터를 처리합니다. 예를 들면 데이터베이스에 저장하거나 로깅하는 등의 작업을 할 수 있습니다.
//        // 이 예제에서는 받은 데이터를 그대로 출력합니다.
//        System.out.println("Received item: " + item.getName() + ", ID: " + item.getId() + ", Description: " + item.getDescription());
//
//        // 클라이언트에게 응답 메시지 반환
//        return ResponseEntity.ok("Item received: " + item.getName());
//    }

    @PostMapping("/api/item")
    public ResponseEntity<String> receiveItemData(@RequestBody Item item) {
        System.out.println("Received input data: ");

        // 클라이언트에게 응답 메시지 반환
        return ResponseEntity.ok("Data received: " );
    }
}