package com.example.test.service;

import java.util.ArrayList;

import com.example.test.domain.Purpose;
import com.example.test.domain.Room;
import com.example.test.util.PageNavigator;

public interface RoomService {

    // 채팅방 create
	Room createRoom(Room room, String purposename);

	// 검색 조건에 따른 채팅방 select
	ArrayList<Room> selectRoomList(PageNavigator navi, String sortType, String enabledType, String purposeType, String wordType, String searchWord);

	// roomid의 Room select
	Room selectRoom(int roomid);

	// Paging
	PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String sortType, String enabledType, String purposeType, String wordType, String searchWord);

	// 카테고리 목록
	ArrayList<Purpose> selectpurposeList();

	// 채팅방 접속 유저수 increase
	int increaseRoom(int roomid);

	// 채팅방 접속 유저수 decrease
	int decreaseRoom(int roomid);

	// 현재 채팅방 접속 유저수
	int checkCount(int roomid);
}
