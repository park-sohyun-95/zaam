package com.example.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHistory {

    int userhistorynum;
    String userid;
    int roomhistorynum;
    int roomid;
    int sleeptimecount;
    int sleeptimetime;
    String sleepimg;
}
