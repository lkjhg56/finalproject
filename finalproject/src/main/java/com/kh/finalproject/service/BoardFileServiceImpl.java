package com.kh.finalproject.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardFileDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.repository.BoardDao;
import com.kh.finalproject.repository.BoardFileDao;


@Service
public class BoardFileServiceImpl implements BoardFileService{
	
	@Autowired
	private BoardDao boardDao;	
	
	@Autowired
	private BoardFileDao boardfileDao;
	
	@Autowired
	private BoardFileDto boardFileDto;
	
	@Autowired
	private SqlSession sqlSession;


	@Override
	public void registWithFile(BoardDto boardDto, List<MultipartFile> board_file) throws IllegalStateException, IOException {
		//게시글등록
		int board_no = boardDao.getsequence();
		
		boardDto.setBoard_no(board_no);
		boardDao.regist(boardDto);
		
		//파일등록
		
		List<BoardFileDto> list = new ArrayList<>();
		
		for(MultipartFile mf : board_file) { //올린 파일 개수만큼 반복
//			int board_file_no = boardDao.getsequence();
			if(!board_file.isEmpty()) {
				list.add(BoardFileDto.builder()
	//												.board_file_no(board_file_no)
													.board_file_upload_name(mf.getOriginalFilename())
													.board_file_size(mf.getSize())
													.board_origin_content_no(board_no)
													.board_file_save_name(UUID.randomUUID().toString())
													.board_file_type(mf.getContentType())
													.build());
			}
//			System.out.println("비어있나요? = " + mf.isEmpty()); 							//비어있냐
//			System.out.println("parameter_name = " + mf.getName());		//파라미터 이름
//			System.out.println("origin = " + mf.getOriginalFilename());		//파일명
//			System.out.println("size = " + mf.getSize());								//파일 사이즈
//			System.out.println("type = " + mf.getContentType()); 			//파일유형
		}
		
		//파일 저장(물리)			
		File dir = new File("D:/upload/board_files");
		dir.mkdirs();
			
			for(int i = 0; i < list.size(); i++) {	
				BoardFileDto dto = list.get(i);//DB에 저장할 파일의 정보 객체
				MultipartFile file = board_file.get(i);//하드디스크에 저장할 실제 파일 객체
				
				File target = new File(dir, dto.getBoard_file_save_name()); //파일을 새로 생성하고 save_name을 가져와 그 이름으로
				file.transferTo(target); //실제파일저장
				
				boardfileDao.fileUpload(dto); //DB저장
			}
			
//			DB저장
//			for(int i=0; i < list.size(); i++) {
//				BoardFileDto dto = list.get(i);
//				boardfileDao.fileUpload(dto);
//			}
//			실제파일 저장
//			for(int i=0; i < board_file.size(); i++) {
//				BoardFileDto dto = list.get(i);
//				File target = new File(dir, dto.getBoard_file_save_name());
//				board_file.get(i).transferTo(target);
//			}
	}
	
	//게시글 수정(+파일수정)
	@Override
	public void editWithFile(BoardDto boardDto, List<MultipartFile> board_file) throws IllegalStateException, IOException {
		
		//게시글 수정
		boardDao.edit(boardDto);
		
		
		List<BoardFileDto> list = new ArrayList<>();
		
		for(MultipartFile mf : board_file) { //올린 파일 개수만큼 반복
			if(!board_file.isEmpty()) {
				list.add(BoardFileDto.builder()
													.board_file_upload_name(mf.getOriginalFilename())
													.board_file_size(mf.getSize())
													.board_origin_content_no(boardDto.getBoard_no())
													.board_file_save_name(UUID.randomUUID().toString())
													.board_file_type(mf.getContentType())
													.build());
			}
		}
		//변경된 파일을 다시 저장.	
		String Path="D:/upload/board_files";
		File dir = new File(Path);
		dir.mkdir();
		
		for(int i = 0; i < list.size(); i++) {	
			MultipartFile mf = board_file.get(i);//하드디스크에 저장할 실제 파일 객체
			
			if(!mf.isEmpty()) {
				//기존 파일의 save와 같으면 삭제하지 않고 틀리면 삭제. 기존 파일은 DB내에서 문제NO를 이용하여 검색하여 붙여야함.
				List<BoardFileDto> dto = sqlSession.selectList("board.getFileNO", boardDto.getBoard_no());
				int board_file_no = dto.get(i).getBoard_file_no();	//file_no받아오기				
				System.out.println("board_file_no = "+board_file_no);
				
				BoardFileDto delete = boardfileDao.getFile(board_file_no); //지울 실제 파일 정보 가져옴
				String filepath = "D:/upload/board_files/"+delete.getBoard_file_save_name();	
				System.out.println("filepath = "+filepath);
				
				File file = new File(filepath);
				file.delete();
				/*******************************************************/
				BoardFileDto boardfiledto = list.get(i);
				boardfileDao.editFile(boardfiledto);
				File target = new File(dir, boardfiledto.getBoard_file_save_name());
				mf.transferTo(target);
				}
	
			
		}
	}
	
//	public void deleteFile2(int board_file_no) {	
//		boardDao.delete(board_no);	//데이터베이스에서 지움
//		boardfileDao.deleteFile(board_file_no);
//		BoardFileDto delete = boardfileDao.getFile(board_file_no); //지울 파일의 실체 가져옴
//		String filepath = "D:/upload/board_files/"+delete.getBoard_file_save_name();
//		File file = new File(filepath);
//		file.delete();
//	}
	

	@Override
	public void regist(BoardDto boardDto) {
		//게시글등록
			int board_no = boardDao.getsequence();
			
			boardDto.setBoard_no(board_no);
			boardDao.regist(boardDto);
		
	}


	
	@Override
	public ResponseEntity<ByteArrayResource> getImg(int board_file_no) throws Exception {
		
		//board_file_no에 대한 파일정보를 가져온다
		BoardFileDto dto = boardfileDao.getFile(board_file_no);
		System.out.println(dto);
		System.out.println("저장명 = " +dto.getBoard_file_save_name());
		

		//directory의 위치에 있는 저장이름으로 파일을 찾아서 불러온 뒤 반환
		//실제 파일을 읽어들인다. (폴더, 파일명)
		File dir = new File("D:/upload/board_files");	
		File file = new File(dir, String.valueOf(dto.getBoard_file_save_name()));
		
		byte[] data = FileUtils.readFileToByteArray(file); //파일을 바이트배열로 변환
		System.out.println("data = "+ data);
		
		
		//헤더 설정 및 전송
		ByteArrayResource resource = new ByteArrayResource(data);
		
		//읽어들인 내용을 사용자에게 전송한다.
		return ResponseEntity.ok()
				//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(dto.getBoard_file_size())
				.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
				.header(HttpHeaders.CONTENT_DISPOSITION, boardfileDao.makeDispositionString(dto))
				.body(resource);				
				
	}


}
		