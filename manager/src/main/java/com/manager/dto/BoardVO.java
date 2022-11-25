package com.manager.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor //인자 없는 기본 생성자 생성
@AllArgsConstructor //모든 전역변수를 인자로 처리하는 생성자
public class BoardVO {

	private int seqno;
	private int seq;
	private String userid;
	private String mwriter;
	private String mtitle;
	private Date mregdate;
	private String mcontent;
	private int hitno;
	private int likecnt;
	private int dislikecnt;
	
	
}
