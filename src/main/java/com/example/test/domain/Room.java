package com.example.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
	int roomid;
	String hostid;
	String roomname;
	String roompw;
	String starttime;
	String endtime;
	int membercount;
	int maxuser;
	int purposenum;
	Boolean enabled;
}
