package com.co.kr.service;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;
import com.co.kr.domain.LoginDomain;
import com.co.kr.vo.FileListVO;

public interface UploadService {
	
	public int fileProcess(FileListVO fileListVO, MultipartHttpServletRequest request, HttpServletRequest httpReq);

	public List<BoardListDomain> boardList();
	//지역게시판
	public List<BoardListDomain> seoulBoard();
	public List<BoardListDomain> gangBoard();
	public List<BoardListDomain> jeonBoard();
	public List<BoardListDomain> chungBoard();
	public List<BoardListDomain> gyeongBoard();
	public List<BoardListDomain> etcBoard();
	
	public List<BoardListDomain> tradeBoard();
	

	public void bdContentRemove(HashMap<String, Object> map);

	public void bdFileRemove(BoardFileDomain boardFileDomain);
	
	public BoardListDomain boardSelectOne(HashMap<String, Object> map);
	
	public List<BoardFileDomain> boardSelectOneFile(HashMap<String, Object> map);
		
}