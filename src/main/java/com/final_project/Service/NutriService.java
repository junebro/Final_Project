package com.final_project.Service;

import com.final_project.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutriService {
    @Autowired
    private MemberMapper memberMapper;

    public String getMemberNickByMemNo(String memno) {
        return memberMapper.getMemberNickByMemNo(memno);
    }
}