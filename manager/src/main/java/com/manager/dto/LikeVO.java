package com.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeVO {

	private int seqno;
	private String userid;
	private String mylikecheck;
	private String mydislikecheck;
	private String likedate;
	private String dislikedate;
	
}
