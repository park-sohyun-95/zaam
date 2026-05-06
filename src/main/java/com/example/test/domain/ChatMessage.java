package com.example.test.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String roomid;
    private String userid;
    private String message;
    private String type;
    private String sessionid;
}
