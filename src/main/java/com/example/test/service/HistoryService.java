package com.example.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.test.domain.RoomHistory;
import com.example.test.domain.UserHistory;

public interface HistoryService {

//	public int chattingStart(RoomHistory roomhistory);

    // 채팅 시작
	public int startChatting(int roomid);

//	public void chattingEnd(RoomHistory roomhistory);

	// 채팅 종료
	public int endChatting(int roomid, int roomhistorynum);

	// roomhistory
	public int getRoomHistory(int roomid);

	// 현재 로그인한 user의 RoomHistory 목록 Select
	public ArrayList<RoomHistory> selectHistoryList(UserDetails user);

	// roomhistorynum의 RoomHistory
	public RoomHistory selectHistoryRoom(int roomhistorynum);

	// 현재 로그인한 user의 RoomHistory
	public RoomHistory selectHistory(UserDetails user);

	// roomhistorynum의 총 졸음 시간
	public Integer selectTotalSleepTime(int roomhistorynum);

	// roomid에서 이뤄진 수업의 평균 졸음 시간
	public Float selectAvgSleepTime(int roomhistorynum, int roomid);

	// roomhistorynum의 SleepKing(가장 많이 잔 사람)
	public List<Map<String, Object>> selectSleepKing(int roomhistorynum);

	// roomhistorynum의 졸았던 사람의 수
	public Integer selectSleepPerson(int roomhistorynum);

	// 졸음 시간 insert
	public int setSleepTime(UserHistory userHistory);

	// UserHistory List Select
	public List<UserHistory> getUserHistory(UserHistory userHistory);

	// 채팅방 입장시 UserHistory insert
	public int setUserHistory(UserHistory userHistory);

	// 유저가 졸 시에 UserTimeHistory insert
	public int setUserTimeHistory(UserHistory userHistory);

	// roomhistorynum의 총 채팅 시간
    public int selectChattingTime(int roomhistorynum);

    // roomhistorynum의 총 참가 인원수
    public int selecttotalPerson(int roomhistorynum);

    // roomhistorynum의 졸았던 모든 시간대
    public ArrayList<String> selectSleepTimeZone1(int roomhistorynum);

    // roomhistorynum와 같은 roomid에서 진행했던 바로 이전의 채팅의 졸았던 모든 시간대
    public ArrayList<String> selectSleepTimeZone2(int roomhistorynum, int roomid);

    // roomhistorynum의 졸았던 모든 UserHistory
    public ArrayList<UserHistory> selectSleepPersonList(int roomhistorynum);

    // roomid에서 roomhistorynum의 순번
    public int selectRoomCount(int roomhistorynum, int roomid);

    // roomhistorynum의 가장 많이 졸았던 사람의 사진
    public String selectSleepKingImage(int roomhistorynum);
}
