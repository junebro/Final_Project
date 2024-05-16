package com.final_project.repository;

import com.final_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    //Optional<Member> findByUserId(String userId);

    // 준형 추가
    Optional<Member> findByMemEmail(String memEmail);
}