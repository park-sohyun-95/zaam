package com.example.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomHistory {
	
	int roomhistorynum;
	int roomid;
	String userid;
	String roomname;
	String starttime;
	String endtime;
}
