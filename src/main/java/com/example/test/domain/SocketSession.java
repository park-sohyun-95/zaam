package com.example.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocketSession {
    private String id;
    private String sessionId;
    private String name;
    private int roomid;
}
