package com.example.test.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.test.domain.Member;

/**
 * 회원정보 관련 매퍼
 */
@Mapper
public interface MemberDAO {

	public int insertMember(Member member);
	
	public ArrayList<Member> selectMemberList();

	public Member selectMember(String loginId);

	public int checkId(int userid);

//메모	
	public int updateMemo(Map<String, Object> map);

    public String selectMemo(Map<String, Object> map);

    public int insertMemo(Map<String, Object> map);

    public ArrayList<Member> selectInviteMemberList(String userid);

}
