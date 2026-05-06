package com.example.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.test.domain.Purpose;
import com.example.test.domain.RoomHistory;
import com.example.test.domain.UserHistory;
import com.example.test.service.HistoryService;
import com.example.test.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @Autowired
    RoomService roomService;

    /**
     * 통계 페이지로 넘어가는 함수
     * @param model 들어가자마자 보여줄 roomhistory에 대한 정보를 담을 Model
     * @param user 현재 로그인한 user
     * @return 통계페이지 history.html
     */
    @GetMapping("/history")
    public String history(Model model
            , @AuthenticationPrincipal UserDetails user) {
        ArrayList<RoomHistory> roomHistoryList = historyService.selectHistoryList(user);
        ArrayList<Map<String, Object>> roomCountList = new ArrayList<Map<String, Object>>();
        
        if(roomHistoryList.size() != 0) {
            for(int i = 0; i < roomHistoryList.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                int roomid = roomHistoryList.get(i).getRoomid();
                int roomhistorynum = roomHistoryList.get(i).getRoomhistorynum();
                int roomCount = historyService.selectRoomCount(roomhistorynum, roomid);
                map.put("roomhistorynum", roomhistorynum);
                map.put("roomCount", roomCount);
                roomCountList.add(map);
            }

            model.addAttribute("roomhistorynum", roomHistoryList.get(0).getRoomhistorynum());
            model.addAttribute("roomid", roomHistoryList.get(0).getRoomid());
            model.addAttribute("roomHistoryList", roomHistoryList);
            model.addAttribute("roomCountList", roomCountList);
        }
        RoomHistory roomHistory = historyService.selectHistory(user);
        
        model.addAttribute("roomHistory", roomHistory);
        model.addAttribute("hostid", user.getUsername());

        return "history";
    }
}
