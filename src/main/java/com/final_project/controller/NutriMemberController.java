package com.final_project.controller;

import com.final_project.Service.NutriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/nutri")
public class NutriMemberController {
    @Autowired
    private NutriService nutriService;

    @PostMapping("/sendUserId01")
    public ResponseEntity<Map<String, String>> receiveUserId(@RequestBody String memno) {
        System.out.println(memno);
        String memberNick = nutriService.getMemberNickByMemNo(memno);
        Map<String, String> response = new HashMap<>();
        if (memberNick != null && !memberNick.equals("Member not found")) {
            response.put("memno", memno);
            response.put("memberNick", memberNick);
            System.out.println("Member Nickname for MemNo " + memno + ": " + memberNick);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Member Nickname not found for MemNo " + memno);
            System.out.println("Member Nickname not found for MemNo " + memno);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
