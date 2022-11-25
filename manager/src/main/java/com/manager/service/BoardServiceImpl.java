package com.manager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.dto.BoardVO;
import com.manager.dto.FileVO;
import com.manager.dto.LikeVO;
import com.manager.dto.ReplyVO;
import com.manager.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper; 
	
	//게시물 목록 보기
	@Override
	public List<BoardVO> list(int startPoint, int endPoint, String searchType,String keyword) throws Exception {
		
		Map<String,Object> map = new HashMap<>();
		map.put("startPoint", startPoint);
		map.put("endPoint", endPoint);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return mapper.list(map);
	}
	
	//전체 게시물 갯수 계산
	@Override
	public int totalCount(String searchType,String keyword) throws Exception{
		Map<String,String> map = new HashMap<>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		return mapper.totalCount(map);
	}

	//게시물 내용 보기
	@Override
	public BoardVO view(int seqno) throws Exception {
		
		return mapper.view(seqno);
	}

	//게시물 등록 
	@Override
	public void write(BoardVO board) throws Exception {
		mapper.write(board);		
	}

	//게시물 번호 구하기 - 시퀀스의 Last Number 사용
	public int getSeqnoWithLastNumber() throws Exception {
		return mapper.getSeqnoWithLastNumber();
	}
	
	//게시물 번호 구하기 - 시퀀스의 nexval 사용
	public int getSeqnoWithNextval() throws Exception {
		return mapper.getSeqnoWithNextval();
	}

	//게시물 이전 보기
	public int pre_seqno(int seqno,String searchType, String keyword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("seqno", seqno);
		map.put("searchType", searchType);
		map.put("keyword", keyword);		
		return mapper.pre_seqno(map);
	}
	
	//게시물 다음 보기
	public int next_seqno(int seqno,String searchType, String keyword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("seqno", seqno);
		map.put("searchType", searchType);
		map.put("keyword", keyword);	
		return mapper.next_seqno(map);
	}
	
	//파일 업로드 정보 등록
	public void fileInfoRegistry(Map<String,Object> fileInfo) throws Exception {
		mapper.fileInfoRegistry(fileInfo);
	}
	
	//게시글 내에서 업로드된 파일 목록 보기
	public List<FileVO> fileListView(int seqno) throws Exception {
		return mapper.fileListView(seqno);
	}

	//다운로드를 위한 파일 정보 보기
	@Override
	public FileVO fileInfo(int fileseqno) throws Exception{
		return mapper.fileInfo(fileseqno);
	}
	
	//게시물 수정
	@Override
	public void modify(BoardVO board) throws Exception {
		mapper.modify(board);		
	}

	//게시물 삭제
	@Override
	public void delete(int seqno) throws Exception {
		mapper.delete(seqno);		
	}

	//게시물 수정에서 파일 삭제
	@Override
	public void deleteFileList(int fileseqno) throws Exception{
		mapper.deleteFileList(fileseqno);
	}

	//게시물 삭제에서 파일 삭제를 위한 파일 목록 가져 오기
	@Override
	public List<FileVO> deleteFileOnBoard(int seqno) throws Exception{
		return mapper.deleteFileOnBoard(seqno);
	}

	//회원 탈퇴 시 회원이 업로드한 파일 정보 가져 오기
	@Override
	public List<FileVO> fileInfoByUserid(String userid) throws Exception{
		return mapper.fileInfoByUserid(userid);
	}
	
	//좋아요/싫어요 확인 가져 오기
	@Override
	public LikeVO likeCheckView(int seqno,String userid) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("userid", userid);
		map.put("seqno", seqno);
		return mapper.likeCheckView(map);
	}
	
	//좋아요/싫어요 갯수 수정하기
	public void boardLikeUpdate(int seqno, int likecnt, int dislikecnt) throws Exception {
		Map<String,Integer> map = new HashMap<>();
		map.put("seqno", seqno);
		map.put("likecnt", likecnt);
		map.put("dislikecnt", dislikecnt);		
		mapper.boardLikeUpdate(map);
	}
	
	//좋아요/싫어요 확인 등록하기
	public void likeCheckRegistry(Map<String,Object> map) throws Exception {
		mapper.likeCheckRegistry(map);
	}
	
	//좋아요/싫어요 확인 수정하기
	public void likeCheckUpdate(Map<String,Object> map) throws Exception {
		mapper.likeCheckUpdate(map);
	}
	
	//댓글 보기
	public List<ReplyVO> replyView(ReplyVO reply) throws Exception{
		return mapper.replyView(reply);
	}
	
	//댓글 수정
	public void replyUpdate(ReplyVO reply) throws Exception{
		mapper.replyUpdate(reply);
	}
	
	//댓글 등록 
	public void replyRegistry(ReplyVO reply) throws Exception{
		mapper.replyRegistry(reply);
	}
	
	//댓글 삭제
	public void replyDelete(ReplyVO reply) throws Exception{
		mapper.replyDelete(reply);
	}
	
	//게시물 조회수 증가
	public void hitnoUpgrade(int seqno) throws Exception{
		mapper.hitnoUpgrade(seqno);				
	}
	
}
