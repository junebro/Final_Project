package com.final_project.Service;

import com.final_project.dto.MemberDTO;
import com.final_project.dto.MypageDTO;
import com.final_project.entity.Diary;
import com.final_project.mapper.MypageMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final MypageMapperInterface mypagemi;

    // 작성 글 조회
    public List<MypageDTO> SelectAll(String userNo) {
        return mypagemi.SelectAll(userNo);
    }

    // 좋아요 조회
    public List<MypageDTO> SelectLike(String userNo) {
        return mypagemi.SelectLike(userNo);
    }

    // 주문내역 조회 하는중
    public List<MypageDTO> SelectOrderAll(String userNo) {
        return mypagemi.SelectAll(userNo);
    }

    public void DeleteMem(String userNo) {
        mypagemi.DeleteMem(userNo);
    }
}
