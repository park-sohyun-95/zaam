package com.example.test.service;

import java.util.ArrayList;
import com.example.test.domain.Member;

/** 
 * 회원정보 관련 서비스
 */
public interface MemberService {

    // 회원가입
	public int insertMember(Member member);

	// 회원 목록
	public ArrayList<Member> selectMemberList();

	// 로그인한 사람의 Member 객체
	public Member selectMember(String loginId);
	
	// 회원가입 시 Id Check
	public int checkId(int userid);
	
	// 메모 Select
	public String selectMemo(int roomid, String userid);
	
	// 메모 Update
	public int updateMemo(String memoContent, int roomid, String userid);
	
	// 메모 Create
	public int createMemo(int roomid, String userid);

	// 초대할 Member의 목록(자신을 제외한 다른 Member)
    public ArrayList<Member> selectInviteMemberList(String userid);
}
