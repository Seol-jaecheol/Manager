package com.manager.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {

	private String userid;
	private String username;
	private String password;
	private String telno;
	private String email;
	private String zipcode;
	private String address;
	private String schoolname;
	private String subject;
	private Date regdate;
	private Date lastlogindate;
	private Date lastlogoutdate;
	private Date lastpwdate;
	private int pwcheck;
	private String role;
	private String org_filename;
	private String stored_filename;
	private long filesize;
	private int pwdiff;

}	