package com.manager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.manager.service.MemberUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
	
	private final AuthSuccessHandler authSuccessHandler;
	private final AuthFailureHandler authFailureHandler;
	private final MemberUserDetailsService memberUserDetailsService;	
	
	
	
	//스프링빈 생성
	@Bean //반드시 만들어야 할 부분
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		
		//람다 표현식
		return (web)->web.ignoring().antMatchers("/images/**","/profile/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
			.antMatchers("/member/**").permitAll()//누구나접근가능
			.antMatchers("/userManage/**").hasAnyAuthority("USER","ADMIN")
			.antMatchers("/board/**").hasAnyAuthority("USER","ADMIN")
			.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
			.anyRequest().authenticated();
		
		//Spring Security를 활용해서 Form문 로그인이 가능하도록 설정
		http
			.formLogin() //UserDetailsService를 상속 받은 클래스내의 override된
			//loadUserByUsername 에서 User객체로 id, password, role 값을 넘김
			.usernameParameter("userid")
			.loginPage("/member/login")
			.successHandler(authSuccessHandler)
			.failureHandler(authFailureHandler);
		
		http.csrf().disable();
		http.cors().disable();
		
		//spring security 에서 제공하는 로그아웃 활성화
		http
			.logout()
			.logoutUrl("/userManage/logout")
			.logoutSuccessUrl("/member/login")
			.invalidateHttpSession(true)
			.deleteCookies("JSEEIONID","remember-me")
			.permitAll();
		
		//세션처리
		http
			.sessionManagement()
			.maximumSessions(1) //허용하는 세션개수--> 동시 접속자 수,-1인경우 무제한세션허용
			.maxSessionsPreventsLogin(false) //true -> 중복 로그인 금지, false-> 이전로그인세션 삭제후 로그인
			.expiredUrl("/member/login?message=SESSION_OUT"); //세션이 만료되었을경우 이동할페이지
		
		//리멤버미 세션기억
		http
			.rememberMe()
			.key("dnsgur1104")
			.alwaysRemember(false) //리멤버미 기능 사용시 항상 세션유지여부 설정
			.tokenValiditySeconds(36000) //10시간 자속
			.rememberMeParameter("remember-me") //로그인창에서 리멤버미 기능을 설정해주는 체크박스 이름
			.userDetailsService(memberUserDetailsService)
			.authenticationSuccessHandler(authSuccessHandler);
			
		log.info("Applecation 접근 권한 설정 완료");
		return http.build();
	}
	

}
