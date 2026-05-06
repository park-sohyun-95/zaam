package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.test.domain.Member;
import com.example.test.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/member")
public class MemberRestController {

	@Autowired
	MemberService memberService;

	/**
	 * 아이디 중복체크 하는 함수
	 * @param userid 현재 사용자가 입력한 userid 값
	 * @return DB에 userid와 같은 값이 저장되어 있는지 count 1이면 존재, 0이면 없음
	 */
	@PostMapping("/checkId")
	public int checkId(@RequestParam("userid") int userid) {
		int cnt = memberService.checkId(userid);
		return cnt;
	}
	
	/**
	 * 회원가입하는 함수
	 * @param member 회원가입 화면에서 입력한 Member 정보
	 * @return 회원가입 완료시 1, 회원가입 실패시 0
	 */
	@PostMapping("/insertMember")
	public int insertMember(Member member) {
		int result = memberService.insertMember(member);
		
		return result;
	}
	
	/**
	 * 채팅방에서 입력한 메모를 업데이트 해주는 함수
	 * @param memoContent 메모가 있는지 없는지 count 1이면 있음, 0이면 없음
	 * @param user 현재 로그인한 사용자 정보
	 * @param roomid 현재 들어가있는 채팅방 roomid
	 * @return 메모를 update한 result, 성공하면 1, 실패하면 0
	 */
	@PostMapping("updateMemo")
	public int updateMemo(String memoContent, @AuthenticationPrincipal UserDetails user, int roomid) {	    
	    int result = memberService.updateMemo(memoContent, roomid, user.getUsername());
	    return result;
	}
	
	
	/**
	 * DB에 저장되어 있는 메모 Select
	 * @param roomid 현재 들어가있는 채팅방 roomid
	 * @param user 현재 로그인한 사용자 정보
	 * @return 현재 DB에 저장되어 있는 메모 content
	 */
	@PostMapping("selectMemo")
	public String selectMemo(int roomid, @AuthenticationPrincipal UserDetails user) {
	    String content = memberService.selectMemo(roomid, user.getUsername());
	    return content;
	}
	
	/**
	 * 메모를 생성하는 함수
	 * @param roomid 현재 들어가있는 채팅방 roomid
	 * @param user 현재 로그인한 사용자 정보
	 * @return 메모 insert 성공 여부 1이면 성공, 0이면 실패
	 */
	@PostMapping("createMemo")
	public int createMemo(int roomid, @AuthenticationPrincipal UserDetails user) {
	    int result = memberService.createMemo(roomid, user.getUsername());
	    return result;
	}
	
}
