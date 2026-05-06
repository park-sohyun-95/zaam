package com.example.test.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.test.dao.HistoryDAO;
import com.example.test.domain.RoomHistory;
import com.example.test.domain.UserHistory;

@Service
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	HistoryDAO historyDAO;

	@Override
	public ArrayList<RoomHistory> selectHistoryList(UserDetails user) {

		ArrayList<RoomHistory> roomHistoryList = historyDAO.selectHistoryList(user.getUsername());

		return roomHistoryList;
	}

	@Override
	public RoomHistory selectHistoryRoom(int roomhistorynum) {

		RoomHistory roomHistory = historyDAO.selectHistoryRoom(roomhistorynum);

		return roomHistory;
	}

	@Override
	public RoomHistory selectHistory(UserDetails user) {

		RoomHistory roomHistory = historyDAO.selectHistoryById(user.getUsername());

		return roomHistory;
	}

	@Override
	public Integer selectTotalSleepTime(int roomhistorynum) {

	    Integer totalSleepTime = historyDAO.selectTotalSleepTime(roomhistorynum);
	    
	    if(totalSleepTime == null) {
	        return 0;
	    }
	    
		return totalSleepTime;
	}

	@Override
	public Float selectAvgSleepTime(int roomhistorynum, int roomid) {

	    Float avgSleepTime = historyDAO.selectAvgSleepTime(roomid);
	    Integer totalSleepTime = historyDAO.selectTotalSleepTime(roomhistorynum);
	    
	    if(avgSleepTime == null || totalSleepTime == null) {
	        return (float) 0;
	    }
	    
	    Float sleepTimeDiff = avgSleepTime - totalSleepTime;

		return sleepTimeDiff;
	}

	@Override
	public int startChatting(int roomid) {
		int result = historyDAO.startChatting(roomid);
		return result;
	}

	@Override
	public int endChatting(int roomid, int roomhistorynum) {
		Map<String, Object> map = new HashMap<>();
		map.put("roomid", roomid);
		map.put("roomhistorynum", roomhistorynum);
		int result = historyDAO.endChatting(map);

		return result;
	}

	@Override
	public int getRoomHistory(int roomid) {
	    
		int result = historyDAO.getRoomHistory(roomid);
		
		return result;
	}

    @Override   
    public List<Map<String, Object>> selectSleepKing(int roomhistorynum) {   
        
        List<Map<String, Object>> sleepKing = historyDAO.selectSleepKing(roomhistorynum);   
        
        return sleepKing;   
    }

	@Override
	public Integer selectSleepPerson(int roomhistorynum) {

	    Integer sleepPerson = historyDAO.selectSleepPerson(roomhistorynum);
	    
	    if(sleepPerson == null) {
            return 0;
        }
		return sleepPerson;
	}

	@Override
	public int setSleepTime(UserHistory userHistory) {

		int result = historyDAO.setSleepTime(userHistory);

		return result;
	}

	@Override
	public List<UserHistory> getUserHistory(UserHistory userHistory) {
		List<UserHistory> list = historyDAO.getUserHistory(userHistory);
		return list;
	}

	@Override
	public int setUserHistory(UserHistory userHistory) {

		int result = historyDAO.createUserHistory(userHistory);

		return result;
	}

	@Override
	public int setUserTimeHistory(UserHistory userHistory) {
		int result = historyDAO.createUserTimeHistory(userHistory);
		return result;
	}
	
	@Override   
	public int selectChattingTime(int roomhistorynum) {  

	    RoomHistory roomhistory = historyDAO.selectChattingTime(roomhistorynum);    
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

	    Date starttime = null;  
	    Date endtime = null;    
	    try {   
	        starttime = format.parse(roomhistory.getStarttime());   
	    } catch (ParseException e) {    
	        // TODO Auto-generated catch block  
	        e.printStackTrace();    
	    }   
	    try {   
	        endtime = format.parse(roomhistory.getEndtime());   
	    } catch (ParseException e) {    
	        // TODO Auto-generated catch block  
	        e.printStackTrace();    
	    }   
	    @SuppressWarnings("null")   
	    int totalChattingTime = (int) ((endtime.getTime() - starttime.getTime()) / 1000);   

	    return totalChattingTime;   
	}   
	@Override   
	public int selecttotalPerson(int roomhistorynum) {   

	    int totalPerson = historyDAO.selectTotalPerson(roomhistorynum); 
	    
	    return totalPerson; 
	}   
	@Override   
	public ArrayList<String> selectSleepTimeZone1(int roomhistorynum) {  

	    ArrayList<String> sleepTimeZone1 = historyDAO.selectSleepTimeZone1(roomhistorynum); 

	    return sleepTimeZone1;  
	}   

	@Override   
	public ArrayList<String> selectSleepTimeZone2(int roomhistorynum, int roomid) {  
	    Map<String, Object> map = new HashMap<String, Object>();    

	    map.put("roomhistorynum", roomhistorynum);  
	    map.put("roomid", roomid);  
	    ArrayList<String> sleepTimeZone2 = historyDAO.selectSleepTimeZone2(map);    

	    return sleepTimeZone2;  
	}   
	@Override   
	public ArrayList<UserHistory> selectSleepPersonList(int roomhistorynum) {    

	    ArrayList<UserHistory> sleepPersonList = historyDAO.selectSleepPersonList(roomhistorynum);  

	    return sleepPersonList; 
	}   
	@Override   
	public int selectRoomCount(int roomhistorynum, int roomid) { 
	    ArrayList<Integer> roomCountList = historyDAO.selectRoomCount(roomid);   

	    for(int i = 0; i < roomCountList.size(); i++) { 
	        if(roomCountList.get(i) == roomhistorynum) {   
	            return i+1;
	        }
	    }
	    return 0;
	}

    @Override
    public String selectSleepKingImage(int roomhistorynum) {
        
        String sleepKingImage = historyDAO.selectSleepKingImage(roomhistorynum);
        
        return sleepKingImage;
    }
}
