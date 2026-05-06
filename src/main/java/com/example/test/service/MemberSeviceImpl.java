package com.example.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.test.dao.MemberDAO;
import com.example.test.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberSeviceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int insertMember(Member member) {
		
		String encodedPassword = passwordEncoder.encode(member.getUserpw());
		member.setUserpw(encodedPassword);
		int result = memberDAO.insertMember(member);

		return result;
	}

	@Override
	public ArrayList<Member> selectMemberList() {

		ArrayList<Member> memberList = memberDAO.selectMemberList();

		return memberList;
	}

	@Override
	public Member selectMember(String loginId) {

		Member member = memberDAO.selectMember(loginId);

		return member;
	}

	@Override
	public int checkId(int userid) {
		int cnt = memberDAO.checkId(userid);
		return cnt;
	}

	//메모
	
    @Override
    public int updateMemo(String memoContent, int roomid, String userid) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("roomid", roomid);
        map.put("memoContent", memoContent);
        log.info("memoContent: {}, userid: {}, roomid: {}", memoContent, userid, roomid);
        int save = memberDAO.updateMemo(map);
        return save;
    }

    @Override
    public String selectMemo(int roomid, String userid) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("roomid", roomid);
        
        String content = memberDAO.selectMemo(map);
        return content;
    }

    @Override
    public int createMemo(int roomid, String userid) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("roomid", roomid);
        
        int result = memberDAO.insertMemo(map);
        return result;
    }

    @Override
    public ArrayList<Member> selectInviteMemberList(String userid) {
        
        ArrayList<Member> inviteMemberList = memberDAO.selectInviteMemberList(userid);
        
        return inviteMemberList;
    }
}
