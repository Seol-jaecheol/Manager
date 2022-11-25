package com.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.manager.dto.MemberVO;
import com.manager.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final MemberService service;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException{
		
		
		//로그인 날짜 등록
		service.logindateUpdate(authentication.getName());
		
		MemberVO member = service.login(authentication.getName());
		
		HttpSession session = request.getSession();
		
		//세션 생성
		//세션 유지 시간 설정 --> 초 단위
		session.setMaxInactiveInterval(3600*7);
		session.setAttribute("userid", member.getUserid());
		session.setAttribute("username", member.getUsername());
		session.setAttribute("role", member.getRole());
		
		log.info("Session 설정 완료");
		
		//패스워드 변경후 30일이 경과했는지 확인
		MemberVO pwcheck = new MemberVO();
		pwcheck = service.pwcheck(member.getUserid());
		
		if(pwcheck.getPwdiff() > 30*pwcheck.getPwcheck()) {
			setDefaultTargetUrl("/userManage/pwCheckNotice");
		}else setDefaultTargetUrl("/userManage/welcome");
		
		super.onAuthenticationSuccess(request, response, authentication);

		
	}
	
}
