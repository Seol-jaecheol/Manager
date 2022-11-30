package com.manager.service;

import java.util.List;

import com.manager.dto.AddressVO;
import com.manager.dto.MemberVO;

public interface MemberService {

	//게시글수
	public int boardcount();

	//아이디 확인
	public int idCheck(String userid); 

	//로그인 정보 확인
	public MemberVO login(String userid); 
	
	//마지막 로드인 시간 등록
	public void logindateUpdate(String userid);
	
	//welcome 페이지 정보 가져 오기 
	public MemberVO welcomeView(String userid);

	//로그아웃 날짜 업데이트
	public void logoutUpdate(String userid);

	//사용자 정보 등록
	public void memberInfoRegistry(String userid);

	//사용자 계정등록목록 삭제
	public void memberInfoReadyDelete(String userid);

	//사용자 계정등록 요청
	public void memberInfoRegistryReady(MemberVO member);

	//사용자 계정등록 리스트
	public List<MemberVO> memberInfoRegistryList();

	//사용자 리스트
	public List<MemberVO> memberList();

	//사용자 계정등록 저세히보기
	public MemberVO memberInfoRegistryDetail(String userid);
	
	//사용자 정보 보기
	public MemberVO memberInfoView(String userid);
	
	//사용자 정보 수정
	public void memberInfoUpdate(MemberVO member);
	
	//패스워드 수정
	public void passwordUpdate(MemberVO member);
	
	//패스워드 변경후 30일 경과 확인
	public MemberVO pwcheck(String userid);
	
	//패스워드 변경 공지 확인 후 30일 이후에 변경
	public void memberpasswordModifyAfter30(String userid);
	
	//사용자 아이디 찾기
	public String searchID(MemberVO member);
	
	//사용자 패스워드 신규 발급을 위한 확인
	public int searchPassword(MemberVO member);
	
	//회원 탈퇴
	public void memberInfoDelete(String userid);
	
	//주소 전체 갯수 계산
	public int addrTotalCount(String addrSearch);
	
	//주소 검색
	public List<AddressVO> addrSearch(int startPoint, int endPoint, String addrSearch);
	
	
}
