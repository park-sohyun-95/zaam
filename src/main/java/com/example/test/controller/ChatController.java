package com.example.test.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.test.domain.ChatMessage;
import com.example.test.domain.Member;
import com.example.test.domain.Room;
import com.example.test.service.MemberService;
import com.example.test.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	RoomService roomService;

	@Autowired
	MemberService memberService;

	/**
	 * 채팅방에 입장하는 함수
	 * @param model 사용자의 여러 정보들을 담을 Model
	 * @param roomid 사용자가 클릭한 roomid
	 * @param user 현재 로그인한 사용자
	 * @return 채팅방 chat.html
	 */
	@GetMapping("/chat")
	public String enter(Model model
			, int roomid
			, @AuthenticationPrincipal UserDetails user){
		String loginId = user.getUsername();
		Room room = roomService.selectRoom(roomid);
		String localId = UUID.randomUUID().toString();
		ArrayList<Member> memberList = memberService.selectMemberList();
        ArrayList<Member> inviteMemberList = memberService.selectInviteMemberList(loginId);

		Member member = memberService.selectMember(loginId);
		model.addAttribute("Member", member);

		log.info("@ChatController, chat GET()");
		model.addAttribute("localId", localId);
		model.addAttribute("loginId", loginId);
		model.addAttribute("room", room);
		model.addAttribute("memberList", memberList);
        model.addAttribute("inviteMemberList", inviteMemberList);

		return "chat";
	}
}
