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

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
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
		
		//1. 기존파일 정보를 불러온다
		//2. 신규파일 유무를 확인한다(board_file 안에 비어있는 객체가 1개 존재)
		//3. 기존파일과 신규파일 유무에 따라 각기 다른 처리를 수행한다
//		System.out.println("board_file@@ = "+board_file);
		if(board_file.get(0).isEmpty()) {//신규 업로드 파일이 없다면 더이상 아무것도 하지 말아라
			return;
		}
		
		//신규 파일이 있는 경우 기존 파일을 먼저 삭제 후 신규 파일을 추가(등록과 동일)
		//(1)기존 파일 모두 삭제하기
		List<BoardFileDto> delete = boardfileDao.getFileNo(boardDto.getBoard_no()); //파일 정보 list형태로 가져오기
		
		//반복문으로 파일 삭제 실행
		for(int i = 0; i < delete.size(); i++) {
			//DB에 저장된 파일 정보 삭제
			int board_no = delete.get(i).getBoard_origin_content_no();
			boardfileDao.deleteFile(board_no);
			
			//실제 파일 삭제
			String filepath = "D:/upload/board_files/" + delete.get(i).getBoard_file_save_name();
			System.out.println("filepath = "+filepath);					
			File file = new File(filepath);
			file.delete();				
		}
		
			//파일 재등록
			//DB에 등록할 파일 정보 설정
			List<BoardFileDto> list = new ArrayList<>();			
			for(MultipartFile mf : board_file) { //올린 파일 개수만큼 반복
					list.add(BoardFileDto.builder()
														.board_file_upload_name(mf.getOriginalFilename())
														.board_file_size(mf.getSize())
														.board_origin_content_no(boardDto.getBoard_no())
														.board_file_save_name(UUID.randomUUID().toString())
														.board_file_type(mf.getContentType())
														.build());
				}
			
			//파일 저장(물리저장)			
			File dir = new File("D:/upload/board_files");
			dir.mkdirs();
				
				for(int i = 0; i < list.size(); i++) {	
					BoardFileDto dto = list.get(i);//DB에 저장할 파일의 정보 객체
					MultipartFile file = board_file.get(i);//하드디스크에 저장할 실제 파일 객체
					
					File target = new File(dir, dto.getBoard_file_save_name()); //파일을 새로 생성하고 save_name을 가져와 그 이름으로
					file.transferTo(target); //실제파일저장
					
					boardfileDao.fileUpload(dto); //DB저장
				}
		
		
//		List<BoardFileDto> list = new ArrayList<>();
//		
//		for(MultipartFile mf : board_file) { //올린 파일 개수만큼 반복
//			if(!board_file.isEmpty()) {
//				list.add(BoardFileDto.builder()
//													.board_file_upload_name(mf.getOriginalFilename())
//													.board_file_size(mf.getSize())
//													.board_origin_content_no(boardDto.getBoard_no())
//													.board_file_save_name(UUID.randomUUID().toString())
//													.board_file_type(mf.getContentType())
//													.build());
//			}
//		}
//		//변경된 파일을 다시 저장.	
//		String Path="C:/upload/board_files";
//		File dir = new File(Path);
//		dir.mkdir();
//		
//		for(int i = 0; i < list.size(); i++) {	
//			MultipartFile mf = board_file.get(i);//하드디스크에 저장할 실제 파일 객체
//			
//			if(!mf.isEmpty()) {
//				//삭제. 기존 파일은 DB내에서 board_no를 이용하여 찾아서 파일 정보를 불러와서 DB삭제, 실제삭제 한다.			
//				List<BoardFileDto> delete = sqlSession.selectList("board.getFileNO", boardDto.getBoard_no()); //지울 실제 파일 정보 가져옴
//				
//				for(int j = 0; j < delete.size(); j++) {	
//					String filepath = "C:/upload/board_files/"+delete.get(j).getBoard_file_save_name();
//					System.out.println("filepath = "+filepath);					
//					File file = new File(filepath);
//					file.delete();					
//				}
//				
//				/*******************************************************/
//				BoardFileDto boardfiledto = list.get(i);
//				boardfileDao.editFile(boardfiledto);
//				File target = new File(dir, boardfiledto.getBoard_file_save_name());
//				mf.transferTo(target);
//			}	
//			
//		}
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

	
	
	@Override
	public void deleteRealfile(int board_no) {
			//삭제. 기존 파일은 DB내에서 board_no를 이용하여 찾아서 파일 정보를 불러와서 DB삭제, 실제삭제 한다.			
			List<BoardFileDto> delete = boardfileDao.getFileNo(board_no);//지울 실제 파일 정보 가져옴
//			for(int i = 0; i < delete.size(); i ++) {
//				log.info("board_file_no{} = ", delete.get(i).getBoard_file_no());
//			}			
			
			if(!delete.isEmpty()) {			
			for(int i = 0; i < delete.size(); i++) {	
				String filepath = "D:/upload/board_files/"+delete.get(i).getBoard_file_save_name();
				System.out.println("filepath = "+filepath);					
				File file = new File(filepath);
				file.delete();					
			}		
		}
	}

}
		