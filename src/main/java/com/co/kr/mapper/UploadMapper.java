package com.co.kr.mapper;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.domain.BoardContentDomain;
import com.co.kr.domain.BoardFileDomain;
import com.co.kr.domain.BoardListDomain;

@Mapper
public interface UploadMapper {
	public List<BoardListDomain> boardList();
	//지역게시판
	public List<BoardListDomain> seoulBoard();
	public List<BoardListDomain> gangBoard();
	public List<BoardListDomain> chungBoard();
	public List<BoardListDomain> jeonBoard();
	public List<BoardListDomain> gyeongBoard();
	public List<BoardListDomain> etcBoard();
	
	
	public List<BoardListDomain> tradeBoard();
	
	
	
	public void contentUpload(BoardContentDomain boardContentDomain);
	public void fileUpload(BoardFileDomain boardFileDomain);
	
	public void bdContentUpdate(BoardContentDomain boardContentDomain);
	public void bdFileUpdate(BoardFileDomain boardFileDomain);
	
	public void bdContentRemove(HashMap<String, Object>map);
	public void bdFileRemove(BoardFileDomain boardFileDomain);

	public BoardListDomain boardSelectOne(HashMap<String, Object> map);
	public List<BoardFileDomain> boardSelectOneFile(HashMap<String, Object> map);

}