package com.party.mapper;

import com.party.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapperInterface {

    @Select("select * from members where id = #{id}") // 게시물 상세 보기
    Member SelectOne(@Param("id") Integer id);

    @Select("select * from members")
    List<Member> SelectAll();
}
