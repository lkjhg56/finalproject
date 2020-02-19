package com.kh.finalproject.repository;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardFileDto;

@Repository
public class BoardFileDaoImpl implements BoardFileDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void fileUpload(BoardFileDto boardfileDto) {
		sqlSession.insert("board.fileUpload", boardfileDto);		
		
	}
	@Override
	public int getsequence() {
		return sqlSession.selectOne("board.filegetSequence");
	}

	private File directory = new File("D:/upload/board_files");		
		@PostConstruct//생성하면서 실행할 메소드(준비메소드)
		public void init() {
			directory.mkdirs();
	}

	@Override
	public void save(String name, MultipartFile board_file) throws IllegalStateException, IOException {
		//물리 저장
		// - 대상 파일 객체 생성 후 저장 명령 실행
		File file = new File(directory, name);
		board_file.transferTo(file);//저장
	}

	@Override
	public byte[] get(int board_file_no) throws IOException {
		//directory의 위치에 있는 board_file_no란 이름의 파일을 찾아서 불러온 뒤 반환
		File file = new File(directory, String.valueOf(board_file_no));
		byte[] data = FileUtils.readFileToByteArray(file);
		return data;
	}
	
	@Override
	public BoardFileDto getFile(int board_file_no) {
		return sqlSession.selectOne("board.getFileOne",board_file_no);		
	}
	@Override
	public List<BoardFileDto> getFile2(int board_file_no) {
		return sqlSession.selectList("board.getFileNO",board_file_no);		
	}
	// 파일 수정을 위한 게시글 파일 번호 가져오기
	@Override
	public List<BoardFileDto> getFileList(int board_origin_content_no) {
		return sqlSession.selectList("board.getFileNO",board_origin_content_no);		
	}

	@Override
	public String makeDispositionString(BoardFileDto boardfileDto) throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("attachment;");
		buffer.append("filename=");
		buffer.append("\"");
		buffer.append(URLEncoder.encode(boardfileDto.getBoard_file_upload_name(), "UTF-8"));
		buffer.append("\"");
		return buffer.toString();
	}

	//게시글 번호로 파일 정보 가져옴
	@Override
	public List<BoardFileDto> getFileNo(int board_no) {
		return sqlSession.selectList("board.getFileNO", board_no);
	}

	//첨부 파일 삭제
	@Override
	public void deleteFile(int board_no) {
		sqlSession.delete("board.deletefile", board_no);		
	}
	@Override
	public void editFile(BoardFileDto boardfileDto) {
		// TODO Auto-generated method stub
		
	}

	



}
