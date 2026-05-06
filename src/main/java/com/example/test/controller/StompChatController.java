package com.example.test.controller;

import com.example.test.service.HistoryService;
import com.example.test.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.example.test.domain.ChatMessage;
import com.example.test.domain.SocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@Log4j2
@RequiredArgsConstructor
public class StompChatController {

    private final ArrayList<SocketSession> sessionIdList;
    private final SimpMessagingTemplate template;
    private final RoomService roomService;
    private final HistoryService historyService;

    /**
     * 채팅방 접속시 입장 채팅 출력
     * @param message 입장 채팅 담을 객체
     * @param roomid 현재 채팅방
     */
    @MessageMapping(value = "/chat/enter/{roomid}")
    public void enter(ChatMessage message, @DestinationVariable int roomid){
        message.setMessage(message.getUserid() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/chat/room/" + roomid, message);
    }

    /**
     * 채팅시에 채팅 출력
     * @param message 채팅 담을 객체
     * @param roomid 현재 채팅방
     */
    @MessageMapping(value = "/chat/message/{roomid}")
    public void message(ChatMessage message, @DestinationVariable int roomid){
        template.convertAndSend("/sub/chat/room/" + roomid, message);
    }
    
    /**
     * 채팅방 퇴장시 퇴장 채팅 출력
     * @param message 퇴장 채팅 담을 객체
     * @param roomid 현재 채팅방
     */
    @MessageMapping(value = "/chat/exit/{roomid}")
    public void exit(ChatMessage message, @DestinationVariable int roomid){
        message.setMessage(message.getUserid() + "님이 퇴장하셨습니다.");
        template.convertAndSend("/sub/chat/room/" + roomid, message);
    }

    /**
     * 방 join 시 시그널링하는 함수
     * @param sessionId 입장한 사용자의 sessionId
     * @param ob 채팅방 입장시 랜덤으로 받는 localId(who), 로그인 아이디(name)를 담은 JSON
     * @param roomid 현재 채팅방
     */
    //Web RTC Signaling Socket Server
    @MessageMapping(value = "/room/join/{roomid}")
    private void joinRoom(@Header("simpSessionId") String sessionId
    		, JSONObject ob
    		, @DestinationVariable int roomid) {
    	sessionIdList.add(new SocketSession((String) ob.get("who"), sessionId, (String)ob.get("name"), roomid));
        List<SocketSession> roomSessionList = sessionIdList.stream().filter(s -> s.getRoomid() == roomid).collect(Collectors.toList());
        roomService.increaseRoom(roomid);
        log.info("roomSessionList: {}", roomSessionList);
        int currentCount = roomService.checkCount(roomid);
        if (currentCount == 1) {
            historyService.startChatting(roomid);
        }
        template.convertAndSend("/sub/history/" + roomid, historyService.getRoomHistory(roomid));
        template.convertAndSend("/sub/room/join/" + roomid, roomSessionList);
    }
    
    /**
     * 채팅방을 입장한 callee가 signal이 오면 응답하는 함수
     * @param ob signal을 보낸 사람의 id(from), 현재 채팅방에 접속중인 사람들(to), 전달 정보(sdp)를 담은 JSON
     * @param roomid 현재 채팅방 
     */
    @MessageMapping(value = "/room/answer/{roomid}")
    private void answer(JSONObject ob, @DestinationVariable int roomid) {
        log.debug("callee ����: {}", ob.toJSONString());

        Map<String, Object> data = new HashMap<>();
        data.put("sdp", ob.get("sdp"));
        data.put("from", ob.get("from"));
        data.put("to", ob.get("to"));
        
        template.convertAndSend("/sub/room/answer/" + roomid, data);
    }
    
    /**
     * 채팅방을 입장한 signal이 오면 기존에 있던 caller들이 응답하는 함수
     * @param ob 현재 로그인한 사람(from), 가장 마지막에 채팅방을 입장한(to),
     * 전달 정보(sdp), 현재 채팅방에서의 수업 번호(roomhistorynum)를 담은 JSON
     * @param roomid 현재 채팅방
     */
    @MessageMapping(value = "/room/offer/{roomid}")
    private void offer(JSONObject ob, @DestinationVariable int roomid) {

        Map<String, Object> data = new HashMap<>();
        data.put("from", ob.get("from"));
        data.put("to", ob.get("to"));
        data.put("sdp", ob.get("sdp"));
        data.put("roomhistorynum", ob.get("roomhistorynum"));

        template.convertAndSend("/sub/room/offer/" + roomid, data);
    }

    /**
     * 발표자 기능 클릭시 함수
     * @param ob 현재 발표를 진행하는 사용자의 id(presenter), 발표 종료 여부(presentover)를 담을 JSON
     * @param roomid 현재 채팅방
     */
    @MessageMapping(value = "/presentation/{roomid}")
    private void present(JSONObject ob, @DestinationVariable int roomid) {
        Map<String, Object> data = new HashMap<>();
        data.put("presenter", ob.get("presenter"));
        data.put("presentover", ob.get("presentover"));
        template.convertAndSend("/sub/presentation/" + roomid, data);
    }
    
    /**
     * 사용자가 졸았다고 판단시 호출 함수
     * @param ob 현재 졸고 있는 사용자 id(sleepid), 졸음 미션 종료 여부(sleepover)를 담은 JSON
     * @param roomid 현재 채팅방
     */
    // 상대방 졸음 판단
    @MessageMapping(value = "/sleepshow/{roomid}")
    private void show(JSONObject ob, @DestinationVariable int roomid) {
        Map<String, Object> data = new HashMap<>();
        data.put("sleepid", ob.get("sleepid"));
        data.put("sleepover", ob.get("sleepover"));
        template.convertAndSend("/sub/sleepshow/" + roomid, data);
    }
    
    /**
     * 
     * @param event
     */
    @EventListener
    private void onSessionDisconnect(SessionDisconnectEvent event) {
        String removedId = "";
        for (SocketSession session: sessionIdList) {
            if (session.getSessionId().equals(event.getSessionId())) {
                removedId = session.getId();
                int roomid = session.getRoomid();
                roomService.decreaseRoom(session.getRoomid());
                int currentCount = roomService.checkCount(roomid);
                if (currentCount <= 0) {
                    int roomhistorynum = historyService.getRoomHistory(roomid);
                    historyService.endChatting(roomid, roomhistorynum);
                }
                sessionIdList.remove(session);
                break;
            }
        }
        template.convertAndSend("/sub/room/exit", removedId);
    }
}
