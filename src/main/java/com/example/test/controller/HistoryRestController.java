package com.example.test.controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.test.domain.RoomHistory;
import com.example.test.domain.UserHistory;
import com.example.test.service.HistoryService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@ResponseBody
@RequestMapping("/history")
@RestController
public class HistoryRestController {

	@Autowired
	HistoryService historyService;
	
    @Value("C:\\workspace\\Zaam\\zaamimages\\")
    String uploadPath;

    /**
     * 클릭한 수업 번호의 통계를 가져오는 함수
     * @param roomhistorynum 클릭한 폼의 방의 수업 번호
     * @param roomid 클릭한 폼의 방 번호
     * @param user 현재 로그인한 유저
     * @return 해당 수업에서 졸았던 사람, 졸았던 사람의 수, 졸았던 총 시간, 졸았던 시간대, 가장 많이 잔 사람, 가장 많이 잔 사람의 이미지
     * 같은 방에서 이뤄진 수업의 바로 이전의 수업에서 졸았던 시간대를 가져옴
     */
	@PostMapping("selectHistory")
	public Map<String, Object> selectHistory(
			int roomhistorynum, int roomid, @AuthenticationPrincipal UserDetails user) {
        Map<String, Object> map = new HashMap<String, Object>();
        RoomHistory roomHistory = historyService.selectHistoryRoom(roomhistorynum);
        List<Map<String, Object>> sleepKingList = historyService.selectSleepKing(roomhistorynum);
        List<String> sleepKing = new ArrayList<String>();
        for(int i = 0; i < sleepKingList.size(); i++) {
            sleepKing.add((String) sleepKingList.get(i).get("USERNAME"));
        }
        Integer sleepPerson = historyService.selectSleepPerson(roomhistorynum);
        Integer totalSleepTime = historyService.selectTotalSleepTime(roomhistorynum);
        int totalChattingTime = historyService.selectChattingTime(roomhistorynum);
        int totalPerson = historyService.selecttotalPerson(roomhistorynum);
        ArrayList<String> sleepTimeZone1 = historyService.selectSleepTimeZone1(roomhistorynum);
        ArrayList<String> sleepTimeZone2 = historyService.selectSleepTimeZone2(roomhistorynum, roomid);
        ArrayList<UserHistory> sleepPersonList = historyService.selectSleepPersonList(roomhistorynum);
        Float sleepTimeDiff = historyService.selectAvgSleepTime(roomhistorynum, roomid);
        String sleepKingImage = historyService.selectSleepKingImage(roomhistorynum);
        
        map.put("roomHistory", roomHistory);
        map.put("sleepKing", sleepKing);
        map.put("sleepPerson", sleepPerson);
        map.put("totalSleepTime", totalSleepTime);
        map.put("totalChattingTime", totalChattingTime);
        map.put("totalPerson", totalPerson);
        map.put("sleepTimeZone1", sleepTimeZone1);
        map.put("sleepTimeZone2", sleepTimeZone2);
        map.put("sleepPersonList", sleepPersonList);
        map.put("sleepTimeDiff", sleepTimeDiff);
        map.put("sleepKingImage", sleepKingImage);

        return map;
	}

	/**
	 * 
	 * @param roomid 
	 * @return
	 */
	@PostMapping("/roomHistory")
	public int getRoomHistory(int roomid) {
		int result = historyService.getRoomHistory(roomid);
		log.debug("roomid받아오나요?:{}", roomid);
		log.debug("result받아오나요?:{}", result);
		return result;
	}

	/**
	 * 유저가 수업에 참여하면 Zaam_User_History 테이블에 컬럼을 생성하여 수업에 참여 했다는 정보 저장
	 * @param user 현재 로그인한 유저
	 * @param roomid 방의 번호
	 * @param roomHistoryNum 현재 수업 번호
	 * @return
	 */
	@PostMapping("/createUserHistory")
	public int createUserHistory(@AuthenticationPrincipal UserDetails user, int roomid, int roomHistoryNum) {
		log.info("roomid: {}, roomHistoryNum: {}", roomid, roomHistoryNum);
		UserHistory userHistory = new UserHistory();
		userHistory.setRoomhistorynum(roomHistoryNum);
		userHistory.setRoomid(roomid);
		userHistory.setUserid(user.getUsername());
		List<UserHistory> list = historyService.getUserHistory(userHistory);
		log.info("list: {}", list);
		if (list.size() == 0) {
			System.out.println("created new UserHistory row: " + historyService.setUserHistory(userHistory));
			return 1;
		}
		return 0;
	}

	/**
	 * 졸음 판단 이후에 졸음이 확인되면 Zaam_User_History에 sleeptimetime과 sleeptimecount를 Update 시켜줌
	 * @param user 현재 로그인한 유저
	 * @param userHistory 해당 유저의 UserHistory
	 * @return
	 */
	@PostMapping("/sleepHistory")
	public int updateSleepHistory(@AuthenticationPrincipal UserDetails user, UserHistory userHistory) {
		userHistory.setUserid(user.getUsername());
		log.debug("sleepinfo의 상태는?:{}", userHistory);
		int result = historyService.setSleepTime(userHistory);
		return result;
	}
	
	// 졸은 시간대 측정
	/**
	 * 
	 * @param user 
	 * @param userHistory 
	 * @return
	 */
	@PostMapping("/sleepTimeHistory")
	public int insertSleepTimeHistory(@AuthenticationPrincipal UserDetails user, UserHistory userHistory) {
		userHistory.setUserid(user.getUsername());
		log.debug("졸은 시간대의 상태는?:{}", userHistory);
		int result = historyService.setUserTimeHistory(userHistory);
		return result;
	}

	/**
	 * 사용자가 졸았을 경우 캡쳐한 화면을 저장하는 함수
	 * @param imageData 캡쳐한 이미지 데이터
	 * @param roomid 현재 접속해있는 roomid
	 * @param roomhistorynum 현재 접속해있는 방의 roomhistorynum
	 * @param user 현재 로그인한 사용자
	 * @return 파일 저장 성공시 "success", 실패시 "error"와 메세지
	 */
	@ResponseBody
	@PostMapping("/sendpic")
	public String savePicture(@RequestParam("imageData") String imageData, int roomid, int roomhistorynum,
							  @AuthenticationPrincipal UserDetails user) {
		String userid = user.getUsername();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		String imgName = userid + "_" + roomhistorynum + "_" +
				(formatter.format(new Date())) + ".png";


		UserHistory userHistory = new UserHistory();
		userHistory.setUserid(userid);
		userHistory.setRoomhistorynum(roomhistorynum);
		userHistory.setRoomid(roomid);
		userHistory.setSleepimg(imgName);

		historyService.setUserTimeHistory(userHistory);

		try {
			byte[] imageByte = Base64.getMimeDecoder().decode(imageData.getBytes());
			//String decodedURL = new String(imageByte);
			String directory = uploadPath + imgName;
			new FileOutputStream(directory).write(imageByte);
			return "success";
		} catch (Exception e) {
			System.out.println(e);
			return "error" + e;
		}

	}
}
