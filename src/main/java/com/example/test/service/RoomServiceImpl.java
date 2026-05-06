package com.example.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.dao.RoomDAO;
import com.example.test.domain.Purpose;
import com.example.test.domain.Room;
import com.example.test.util.PageNavigator;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomDAO roomDAO;
	
	@Override
	public Room createRoom(Room room, String purposename) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("room", room);
		map.put("purposename", purposename);

		roomDAO.createRoom(map);
		
		Room insertedRoom = roomDAO.selectRoom(map.get("roomid"));
		
		return insertedRoom;
	}

	@Override
	public ArrayList<Room> selectRoomList(PageNavigator navi, String sortType, String enabledType
			, String purposeType, String wordType, String searchWord) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("sortType", sortType);
		map.put("purposeType", purposeType);
		map.put("wordType", wordType);
		map.put("searchWord", searchWord);

		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());

		ArrayList<Room> roomList = roomDAO.selectRoomList(map, rb);
		return roomList;
	}

	@Override
	public Room selectRoom(int roomid) {
		
		Room room = roomDAO.selectRoom(roomid);
		
		return room;
	}

	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String sortType,
			String enabledType, String purposeType, String wordType, String searchWord) {

		HashMap<String, String> map = new HashMap<>();
		map.put("sortType", sortType);
		map.put("purposeType", purposeType);
		map.put("wordType", wordType);
		map.put("searchWord", searchWord);
		
		int total = roomDAO.count(map);
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);

		return navi;
	}

	@Override
	public ArrayList<Purpose> selectpurposeList() {
		
		ArrayList<Purpose> purposeList = roomDAO.selectpurposeList();
		
		return purposeList;
	}

	@Override
	public int increaseRoom(int roomid) {
		int result = roomDAO.increaseRoom(roomid);
		return result;
	}

	@Override
	public int decreaseRoom(int roomid) {
		int result = roomDAO.decreaseRoom(roomid);
		return result;
	}

	@Override
	public int checkCount(int roomid) {
		int result = roomDAO.countRoom(roomid);
		return result;
	}
}
