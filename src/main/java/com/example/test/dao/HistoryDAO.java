package com.example.test.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.test.domain.RoomHistory;
import com.example.test.domain.UserHistory;

@Mapper
public interface HistoryDAO {

	int startChatting(int roomid);

	int endChatting(Map<String, Object> map);

	int getRoomHistory(int roomid);

	ArrayList<RoomHistory> selectHistoryList(String userid);

	RoomHistory selectHistoryRoom(int roomhistorynum);

	RoomHistory selectHistoryById(String username);

	Float selectAvgSleepTime(int roomid);

	Integer selectTotalSleepTime(int roomhistorynum);

	List<Map<String, Object>> selectSleepKing(int roomhistorynum);

	Integer selectSleepPerson(int roomhistorynum);

    public int setSleepTime(UserHistory userHistory);

	public List<UserHistory> getUserHistory(UserHistory userHistory);
	
	public int createUserHistory(UserHistory userHistory);

	public int createUserTimeHistory(UserHistory userHistory);
	
    RoomHistory selectChattingTime(int roomhistorynum);

    int selectTotalPerson(int roomhistorynum);

    ArrayList<String> selectSleepTimeZone1(int roomhistorynum);

    ArrayList<String> selectSleepTimeZone2(Map<String, Object> map);

    ArrayList<UserHistory> selectSleepPersonList(int roomhistorynum);

    ArrayList<Integer> selectRoomCount(int roomid);

    String selectSleepKingImage(int roomhistorynum);
}
