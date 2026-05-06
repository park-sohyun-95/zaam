package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.test.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	RoomService roomService;

	/**
	 * 로그인과 회원가입 화면으로 이동하는 함수
	 * @return 로그인과 회원가입 화면
	 */
	@GetMapping("/loginForm")	
	public String loginForm() {
		return "loginForm";	
	}

	/**
	 * 로그아웃할 때 이동할 함수
	 * @return 새로고침
	 */
	@GetMapping("/logout")	
	public String logout() {
		return "redirect:/";
	}
}
