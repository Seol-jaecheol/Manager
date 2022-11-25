package com.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manager.dto.MemberVO;
import com.manager.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor // final 또는 @NoNull 이 붙은 전역변수만 선택해서 생성자를 이용해서 초기화
@Service
@Log4j2
public class MemberUserDetailsService implements UserDetailsService {

	private final MemberMapper mapper;//의존성 주입
	//private MemberMapper mapper;
	//public MemberUserDetailsService(MemberMapper mapper){
	//	this.mapper = mapper;
	//}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//username --> id, 죽 여기선 userid
		
		log.info("loadUserByUsernamed에서 받은 userid = {}",username);
		
		MemberVO member = mapper.memberInfoView(username);
		if(member ==null)
			throw new UsernameNotFoundException("아이디/패스워드가 부정확합니다.");
		
		//로그인한 사용자의 Role 정보를 가졍ㅘ서 List 타이브로 전환
		List grantedAuthorities = new ArrayList<>();
		SimpleGrantedAuthority grantedAuthority
			=new SimpleGrantedAuthority(member.getRole());
		grantedAuthorities.add(grantedAuthority);
		
		//spring Security를 사용할 때에는 최종적으로 로그인할 사용자의
		//어아디 패스워드 Role 을 User 객체에 넣어줘야 한다
		User user = new User(username,member.getPassword(),grantedAuthorities);
		
		log.info("User 객체로 넘겨준 값들. userid = {},role = {}",username,member.getRole());
		
		return user;
	}
	
}
