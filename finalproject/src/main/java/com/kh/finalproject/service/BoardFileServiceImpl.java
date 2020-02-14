package com.kh.finalproject.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardFileDto;
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
		BoardFileDto dto = boardDao.getFile(board_file_no);
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
				.header(HttpHeaders.CONTENT_DISPOSITION, boardDao.makeDispositionString(dto))
				.body(resource);				
				
	}
	
	
	
}
		