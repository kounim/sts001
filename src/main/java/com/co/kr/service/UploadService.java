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

	public void bdContentRemove(HashMap<String, Object> map);

	public void bdFileRemove(BoardFileDomain boardFileDomain);
		
}