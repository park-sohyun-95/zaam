package com.example.test.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.domain.Purpose;
import com.example.test.domain.Room;
import com.example.test.domain.RoomHistory;
import com.example.test.service.HistoryService;
import com.example.test.service.RoomService;
import com.example.test.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/room")
public class RoomController {

	@Autowired
	RoomService roomService;

	@Autowired
	HistoryService historyService;
	
	@Value("${room.list.page}")
	int countPerPage;
	
	@Value("${room.list.group}")
	int pagePerGroup;
	
	/**
	 * 로그인 후에 나오는 방 목록 화면
	 * @param model 여러가지 정보 담을 Model
	 * @param page 현재 선택된 Page
	 * @param sortType 정렬 기준
	 * @param enabledType 수강중, 수강종료
	 * @param purposeType 카테고리
	 * @param wordType 검색 타입
	 * @param searchWord 검색 단어
	 * @return 채팅방 목록 화면
	 */
	@GetMapping("/roomList")
    public String roomList2(
    		Model model
			, @RequestParam(name="page", defaultValue="1") int page
			, String sortType
			, String enabledType
			, @RequestParam(name="purposeType", defaultValue="") String purposeType
			, String wordType
			, String searchWord) {
	    
		PageNavigator navi = roomService.getPageNavigator(
				pagePerGroup, countPerPage, page, sortType, enabledType, purposeType, wordType, searchWord);
		
    	ArrayList<Room> roomList = roomService.selectRoomList(navi, sortType, enabledType, purposeType, wordType, searchWord);
    	ArrayList<Purpose> purposeList = roomService.selectpurposeList();
    	
    	model.addAttribute("roomList", roomList);
    	model.addAttribute("purposeList", purposeList);
		model.addAttribute("sortType", sortType);
		model.addAttribute("purposeType", purposeType);
		model.addAttribute("wordType", wordType);
		model.addAttribute("searchWord", searchWord);
    	model.addAttribute("navi", navi);
    	return "roomList";
    }
}
