package com.example.test.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.test.domain.Purpose;
import com.example.test.domain.Room;

@Mapper
public interface RoomDAO {

	void createRoom(Map<String, Object> map);

	ArrayList<Room> selectRoomList(HashMap<String, Object> map, RowBounds rb);

	Room selectRoom(Object roomid);

	int count(HashMap<String, String> map);

	ArrayList<Purpose> selectpurposeList();

	int increaseRoom(int roomid);
	
	int decreaseRoom(int roomid);
	
	int countRoom(int roomid);

}
