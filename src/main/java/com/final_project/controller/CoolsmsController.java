package com.final_project.controller;

import com.final_project.Service.CertificationService;
import com.final_project.dto.InputData;
import com.final_project.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Random;

@RestController
public class CoolsmsController {

    @Autowired
    private CertificationService certificationService;

    @PostMapping("/check/sendsms")
    public @ResponseBody String sendSMS(@RequestBody InputData inputData) {
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        System.out.println("수신자 번호 : " + inputData.toString());
        System.out.println("인증번호 : " + numStr);
        certificationService.certifiedPhoneNumber(inputData.toString(),numStr);
        return numStr;
    }

//    @PostMapping("/check/sendsms")
//    public ResponseEntity<String> receiveItemData(@RequestBody Item item) {
//        // 여기서 입력받은 데이터를 처리합니다. 예를 들면 데이터베이스에 저장하거나 로깅하는 등의 작업을 할 수 있습니다.
//        // 이 예제에서는 받은 데이터를 그대로 출력합니다.
//        System.out.println("sdfsdf");
//
//        // 클라이언트에게 응답 메시지 반환
//        return ResponseEntity.ok("Item received: fgfgf" );
//    }
}
