package com.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.manager.dto.AddressVO;
import com.manager.dto.MemberVO;
import com.manager.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper; 
	
	//게시글수
	@Override
	public int boardcount(){
		return mapper.boardcount();
	}

	//아이디 확인
	@Override
	public int idCheck(String userid) {
		return mapper.idCheck(userid);
	}

	//로그인 정보 확인
	@Override
	public MemberVO login(String userid) {
		return mapper.login(userid);
	}

	//마지막 로그인 날짜 등록/수정
	@Override
	public void logindateUpdate(String userid) {
		mapper.logindateUpdate(userid);
	}

	//welcome 페이지 정보 가져 오기 
	public MemberVO welcomeView(String userid) {
		return mapper.welcomeView(userid);
	}

	//로그 아웃 날짜 등록
	@Override
	public void logoutUpdate(String userid) {
		mapper.logoutUpdate(userid);
	}

	//사용자 등록
	@Override
	public void memberInfoRegistry(String userid) {
		mapper.memberInfoRegistry(userid);
	}

	//사용자 계정요청목록 삭제
	@Override
	public void memberInfoReadyDelete(String userid) {
		mapper.memberInfoReadyDelete(userid);
	}

	//사용자 계정요청 등록
	@Override
	public void memberInfoRegistryReady(MemberVO member) {
		mapper.memberInfoRegistryReady(member);
	}

	//사용자 계정요청 리스트
	@Override
	public List<MemberVO> memberInfoRegistryList() {
		return mapper.memberInfoRegistryList();
	}

	//사용자 리스트
	@Override
	public List<MemberVO> memberList() {
		return mapper.memberList();
	}

	//사용자 계정요청 자세히보기
	@Override
	public MemberVO memberInfoRegistryDetail(String userid) {
		return mapper.memberInfoRegistryDetail(userid);
	}

	//사용자 정보 보기
	@Override
	public MemberVO memberInfoView(String userid) {
		return mapper.memberInfoView(userid);
	}
	
	//사용자 정보 수정
	@Override
	public void memberInfoUpdate(MemberVO member) {
		mapper.memberInfoUpdate(member);		
	}

	//사용자 패스워드 변경
	@Override
	public void passwordUpdate(MemberVO member) {
		mapper.passwordUpdate(member);		
	}
	
	//패스워드 변경후 30일 경과 확인
	@Override
	public MemberVO pwcheck(String userid) {
		return mapper.pwcheck(userid);
	}
	
	//패스워드 변경 공지 확인 후 30일 이후에 변경
	@Override
	public void memberpasswordModifyAfter30(String userid) {
		mapper.memberpasswordModifyAfter30(userid);
	}

	//사용자 아이디 찾기
	@Override
	public String searchID(MemberVO member) {
		return mapper.searchID(member);
	}

	//사용자 패스워드 신규 발급을 위한 확인
	@Override
	public int searchPassword(MemberVO member) {
		return mapper.searchPassword(member);
	}

	//회원 탈퇴
	@Override
	public void memberInfoDelete(String userid) {
		mapper.memberInfoDelete(userid);		
	}

	//우편번호 전체 갯수 가져 오기
	@Override
	public int addrTotalCount(String addrSearch) {
		return mapper.addrTotalCount(addrSearch);
	}

	//우편번호 검색
	@Override
	public List<AddressVO> addrSearch(int startPoint, int endPoint, String addrSearch) {
		Map<String,Object> map = new HashMap<>();
		map.put("startPoint", startPoint);
		map.put("endPoint", endPoint);
		map.put("addrSearch", addrSearch);
		return mapper.addrSearch(map);
	}

}
