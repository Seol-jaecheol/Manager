package com.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyVO {

	private int replyseqno;
	private int seqno;
	private String replywriter;
	private String replycontent;
	private String replyregdate;
	private String userid;

}
