package com.example.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.test.domain.Room;
import com.example.test.service.RoomService;
import com.example.test.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@ResponseBody
@RequestMapping("/room")
public class RoomRestController {

	@Autowired
	RoomService roomService;
	
	@Value("${room.list.page}")
	int countPerPage;
	
	@Value("${room.list.group}")
	int pagePerGroup;
	
	/**
	 * 방 생성 함수
	 * @param room 방 생성시에 필요한 정보를 담은 객체
	 * @param user 현재 로그인한 사용자
	 * @param purposename 방 생성시에 선택한 카테고리 이름
	 * @return 방금 생성한 방(roomid를 받은 상태)
	 */
	@PostMapping("/createRoom")
	public Room createRoom(Room room, @AuthenticationPrincipal UserDetails user, String purposename) {
		String hostId = user.getUsername();
		room.setHostid(hostId);
		
		Room insertedRoom = roomService.createRoom(room, purposename);
		
		return insertedRoom;
	}
	
	/**
	 * 카테고리, 검색, 정렬 등 채팅방 검색 함수
	 * @param page 현재 페이지
     * @param sortType 정렬 기준
     * @param enabledType 수강중, 수강종료
     * @param purposeType 카테고리
     * @param wordType 검색 타입
     * @param searchWord 검색 단어
	 * @return 검색된 채팅방 목록과 navigator
	 */
	@PostMapping("/searchRoom")
	public Map<String, Object> searchRoom(@RequestParam(name="page", defaultValue="1") int page
			, String sortType
			, String enabledType
			, @RequestParam(name="purposeType", defaultValue="") String purposeType
			, String wordType
			, String searchWord) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageNavigator navi = roomService.getPageNavigator(
				pagePerGroup, countPerPage, page, sortType, enabledType, purposeType, wordType, searchWord);

    	ArrayList<Room> roomList = roomService.selectRoomList(navi, sortType, enabledType, purposeType, wordType, searchWord);
    	
    	map.put("roomList", roomList);
    	map.put("navi", navi);
    	
		return map;
	}
}
