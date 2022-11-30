package com.manager.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.manager.dto.AddressVO;
import com.manager.dto.FileVO;
import com.manager.dto.MemberVO;
import com.manager.service.BoardService;
import com.manager.service.MemberService;
import com.manager.util.Page;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

	private final MemberService service;
	private final BoardService boardService;
	private final BCryptPasswordEncoder pwdEncoder;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

	//사용자 등록 화면 보기
	@RequestMapping(value="/member/signup",method=RequestMethod.GET)
	public void getMemberRegistry() throws Exception { }
	
	//사용자 등록 처리
	@RequestMapping(value="/member/signup",method=RequestMethod.POST)
	public String postMemberRegistry(MemberVO member,
			@RequestParam("fileUpload") MultipartFile multipartFile ) {
		
		String path = "c:\\Repository\\profile\\";
		File targetFile;
		
		if(!multipartFile.isEmpty()) {
				
				String org_filename = multipartFile.getOriginalFilename();	
				String org_fileExtension = org_filename.substring(org_filename.lastIndexOf("."));	
				String stored_filename =  UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
								
				try {
					targetFile = new File(path + stored_filename);
					
					multipartFile.transferTo(targetFile);
					
					member.setOrg_filename(org_filename);
					member.setStored_filename(stored_filename);
					member.setFilesize(multipartFile.getSize());
																				
				} catch (Exception e ) { e.printStackTrace(); }
				
				String inputPassword = member.getPassword();
				String pwd = pwdEncoder.encode(inputPassword); 
				member.setPassword(pwd);			
				
		}	

		service.memberInfoRegistryReady(member);
		return "redirect:/";
	}

	//대시보드
	@RequestMapping(value="/userManage/dashboard",method=RequestMethod.GET)
	public void getdashboard(HttpSession session, Model model) throws Exception{
		
		String userid = (String)session.getAttribute("userid");		
	   	List<MemberVO> memcount = service.memberList();
		List<MemberVO> memcountReady = service.memberInfoRegistryList();
		int boardcount = service.boardcount();

	  	model.addAttribute("memcount", memcount);
		model.addAttribute("memcountReady", memcountReady);
		model.addAttribute("boardcount", boardcount);

	}
	
	//지도보기
	@RequestMapping(value="/userManage/map",method=RequestMethod.GET)
	public void getMap() { }
	
	//멤버 목록
	@RequestMapping(value="/userManage/memberList",method=RequestMethod.GET)
	public void memberList(HttpSession session, Model model) throws Exception{
	   

	   
	   String userid = (String)session.getAttribute("userid");		
	   List<MemberVO> member = service.memberList();
	   model.addAttribute("list", member);
	}


	//계정 요청 목록 보기
	@RequestMapping(value="/userManage/regestList",method=RequestMethod.GET)
	public void regestList(HttpSession session, Model model) throws Exception{
	   

	   
	   String userid = (String)session.getAttribute("userid");		
	   List<MemberVO> member = service.memberInfoRegistryList();
	   model.addAttribute("list", member);
	}

	//계정 요청 자세히 보기
	@RequestMapping(value="/userManage/memberInfoRegest",method=RequestMethod.GET)
	public void getmemberInfoRegest(HttpSession session, Model model, @RequestParam("userid") String userid) throws Exception{
	   
	
	   MemberVO member = service.memberInfoRegistryDetail(userid);
	   model.addAttribute("list", member);
	}

	//계정 요청 자세히 보기 > 등록
	@RequestMapping(value="/userManage/memberInfoRegestR",method=RequestMethod.POST)
	public String postmemberInfoRegestR(MemberVO member,
			@RequestParam("userid") String userid ) {
	

	service.memberInfoRegistry(userid);
	service.memberInfoReadyDelete(userid);
	return "redirect:/userManage/regestList";
		
	}

	//계정 요청 자세히 보기 > 목록에서 삭제
	@RequestMapping(value="/userManage/memberInfoRegestRM",method=RequestMethod.POST)
	public String postmemberInfoRegestRM(MemberVO member,
			@RequestParam("userid") String userid ) {
	

	service.memberInfoReadyDelete(userid);
	return "redirect:/userManage/regestList";
		
	}
	

	//사용자 등록 시 아이디 중복 확인
	@ResponseBody //Ajax 자바스크립트 함수와 값을 비동기로 교환하는 메소드 앞에는 반드시 @ResponseBody
	@RequestMapping(value="/member/idCheck",method=RequestMethod.POST)
	public int idCheck(@RequestParam("userid") String userid) throws Exception{
		
		//0,1 값이 들어 온다
		int result = service.idCheck(userid);
		
		return result;
	}
	
	//로그인 화면 보기
	@RequestMapping(value="/member/login",method=RequestMethod.GET)
	public void getLogIn() { }
	
	//로그인 처리
	@RequestMapping(value="/member/login",method=RequestMethod.POST)
	public void postLogIn() {}
	
	//welcome 페이지 정보 가져 오기
	@RequestMapping(value="/userManage/welcome",method=RequestMethod.GET)
	public void getWelcomeView(HttpSession session,Model model) {
		
		String userid = (String)session.getAttribute("userid");
		String username = (String)session.getAttribute("username");
		
		MemberVO member = service.welcomeView(userid);
		
		String Lastlogindate = simpleDateFormat.format(member.getLastlogindate());
		String Regdate = simpleDateFormat.format(member.getRegdate());

		model.addAttribute("userid", userid);
		model.addAttribute("username", username);
		model.addAttribute("regdate",Regdate);
		model.addAttribute("lastlogindate", Lastlogindate);
		model.addAttribute("lastlogoutdate", member.getLastlogoutdate());
		
	}
	
	//로그아웃
	@RequestMapping(value="/userManage/logout",method=RequestMethod.GET)
	public void getLogout(HttpSession session,Model model) {
		
		String userid = (String)session.getAttribute("userid");
		String username = (String)session.getAttribute("username");

		//로그 아웃 날짜 등록
		service.logoutUpdate(userid);
		
		model.addAttribute("userid", userid);
		model.addAttribute("username", username);
		
		//모든 세션값 삭제 --> 로그아웃...
		session.invalidate(); 
		
	}

	//사용자 정보 보기
	@RequestMapping(value="/userManage/memberInfo",method=RequestMethod.GET)
	public void gerMemberInfoView(Model model,HttpSession session) {
		
		String userid = (String)session.getAttribute("userid");
		MemberVO member = service.memberInfoView(userid);
		MemberVO member_date = service.welcomeView(userid);
		String Lastlogindate = simpleDateFormat.format(member_date.getLastlogindate());
		String Regdate = simpleDateFormat.format(member_date.getRegdate());
		
		model.addAttribute("member", member);
		model.addAttribute("lastlogindate", Lastlogindate);
		model.addAttribute("regdate", Regdate);
		
	}
	
	//사용자 정보 수정 보기
	@RequestMapping(value="/userManage/memberInfoModify",method=RequestMethod.GET)
	public void getMemberInfoModify(Model model,HttpSession session) {
		
		String userid = (String)session.getAttribute("userid");
		MemberVO member = service.memberInfoView(userid);
		MemberVO member_date = service.welcomeView(userid);
		
		model.addAttribute("member", member);
		model.addAttribute("member_date", member_date);
		
	}
	
	//사용자 정보 수정
	@RequestMapping(value="/userManage/memberInfoModify",method=RequestMethod.POST)
	public String postMemberInfoModify(MemberVO member,
			@RequestParam("fileUpload") MultipartFile multipartFile ) {
	
	String path_profile = "c:\\Repository\\profile\\";
	File targetFile;
	
	if(!multipartFile.isEmpty()) {

		//기존 프로파일 이미지 삭제
		MemberVO vo = new MemberVO();
		vo = service.memberInfoView(member.getUserid());
		File file = new File(path_profile + vo.getStored_filename());
		file.delete();
		
		String org_filename = multipartFile.getOriginalFilename();	
		String org_fileExtension = org_filename.substring(org_filename.lastIndexOf("."));	
		String stored_filename =  UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	
						
		try {
			targetFile = new File(path_profile + stored_filename);
			
			multipartFile.transferTo(targetFile);
			
			member.setOrg_filename(org_filename);
			member.setStored_filename(stored_filename);
			member.setFilesize(multipartFile.getSize());
																		
		} catch (Exception e ) { e.printStackTrace(); }
			
	}	

	service.memberInfoUpdate(member);
	return "redirect:/userManage/memberInfo";
		
	}
	
	//사용자 패스워드 변경 보기
	@RequestMapping(value="/userManage/memberPasswordModify",method=RequestMethod.GET)
	public void getMemberPasswordModify() { }
	
	//사용자 패스워드 변경 
	@RequestMapping(value="/userManage/memberPasswordModify",method=RequestMethod.POST)
	public String postMemberPasswordModify(@RequestParam("old_userpassword") String old_password,
			@RequestParam("new_userpassword") String new_password, HttpSession session) { 
		
		String userid = (String)session.getAttribute("userid");
		
		MemberVO member = service.memberInfoView(userid);
		if(pwdEncoder.matches(old_password, member.getPassword())) {
			member.setPassword(pwdEncoder.encode(new_password));
			service.passwordUpdate(member);
		}	
		return "redirect:/userManage/logout";
	}
	
	//패스워드30일 경과창 보기
	@RequestMapping(value="/userManage/pwCheckNotice",method=RequestMethod.GET)
	public void getpwCheckNotice() { } 
	
	//패스워드 30일이후에 변경 공지 나오도록 pwcheckㄱ밧 변경
	@RequestMapping(value="/userManage/memberPasswordModifyAfter30",method=RequestMethod.GET)
	public String getmemberPasswordModifyAfter30(HttpSession session) {
		service.memberpasswordModifyAfter30((String)session.getAttribute("userid"));
		return "redirect:/userManage/welcome";
	}
	
	
	//사용자 아이디 찾기 보기
	@RequestMapping(value="/member/searchID",method=RequestMethod.GET)
	public void getSearchID() { } 
	
	//사용자 아이디 찾기 
	@RequestMapping(value="/member/searchID",method=RequestMethod.POST)
	public String postSearchID(MemberVO member, RedirectAttributes rttr) { 
		
		String userid = service.searchID(member);
				
		//조건에 해당하는 사용자가 아닐 경우 
		if(userid == null ) { 
			rttr.addFlashAttribute("msg", "ID_NOT_FOUND");
			return "redirect:/member/searchID"; 
		}
		
		return "redirect:/member/IDView?userid=" + userid;		
	} 

	//찾은 아이디 보기
	@RequestMapping(value="/member/IDView",method=RequestMethod.GET)
	public void postSearchID(@RequestParam("userid") String userid, Model model) {
		
		model.addAttribute("userid", userid);
		
	}
	
	//사용자 패스워드 임시 발급 보기
	@RequestMapping(value="/member/searchPassword",method=RequestMethod.GET)
	public void getSearchPassword() { } 
	
	
	//사용자 패스워드 임시 발급
	@RequestMapping(value="/member/searchPassword",method=RequestMethod.POST)
	public String postSearchPassword(MemberVO member, RedirectAttributes rttr) { 
		
		if(service.searchPassword(member)==0) {
			
			rttr.addFlashAttribute("msg", "PASSWORD_NOT_FOUND");
			return "redirect:/member/searchPassword"; 
			
		}
		
		//숫자 + 영문대소문자 7자리 임시패스워드 생성
		StringBuffer tempPW = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 7; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z : 아스키코드 97~122
		    	tempPW.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z : 아스키코드 65~122
		    	tempPW.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		    	tempPW.append((rnd.nextInt(10)));
		        break;
		    }
		}
		
		member.setPassword(pwdEncoder.encode(tempPW));
		service.passwordUpdate(member);
			
		return "redirect:/member/tempPWView?password=" + tempPW;
		
	} 
	
	//발급한 임시패스워드 보기
	@RequestMapping(value="/member/tempPWView",method=RequestMethod.GET)
	public void getTempPWView(Model model, @RequestParam("password") String password) {
		
		model.addAttribute("password", password);
		
	}
		
	//회원탈퇴
	@RequestMapping(value="/userManage/memberInfoDelete",method=RequestMethod.GET)
	public String getDeleteMember(HttpSession session) throws Exception {
		
		String userid = (String)session.getAttribute("userid"); 
		
		String path = "c:\\Repository\\profile\\";
		
		//회원 프로필 사진 삭제
		MemberVO member = new MemberVO();
		member = service.memberInfoView(userid);		
		File file = new File(path + member.getStored_filename());
		file.delete();
		
		//회원이 업로드한 파일 삭제
		List<FileVO> fileList = boardService.fileInfoByUserid(userid);
		for(FileVO vo:fileList) {
			File f = new File(path + vo.getStored_filename());
			f.delete();
		}
		
		//게시물,댓글,파일업로드 정보, 회원정보 전체 삭제
		service.memberInfoDelete((String)session.getAttribute("userid"));
		
		//모든 세션 삭제
		session.invalidate();
		
		return "redirect:/";
	}
		
	//우편번호 검색
	@RequestMapping(value="/member/addrSearch",method=RequestMethod.GET)
	public void getSearchAddr(@RequestParam("addrSearch") String addrSearch,
			@RequestParam("page") int pageNum,Model model) throws Exception {
		
		int postNum = 5;
		int startPoint = (pageNum -1)*postNum + 1; //테이블에서 읽어 올 행의 위치
		int endPoint = pageNum*postNum;
		int listCount = 5;
		
		Page page = new Page();
		
		int totalCount = service.addrTotalCount(addrSearch);
		List<AddressVO> list = new ArrayList<>();
		list = service.addrSearch(startPoint, endPoint, addrSearch);

		model.addAttribute("list", list);
		model.addAttribute("pageListView", page.getPageAddress(pageNum, postNum, listCount, totalCount, addrSearch));
		
	}
	
}
