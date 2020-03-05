package com.kh.finalproject.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UserFileDto;
import com.kh.finalproject.entity.UsersDto;
import com.kh.finalproject.repository.GradePointDao;
import com.kh.finalproject.repository.UserFileDao;
import com.kh.finalproject.repository.UsersDao;

public class UserFileServiceImpl implements UserFileService{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private UserFileDao userFileDao;
	
	@Autowired
	private UserFileDto userFileDto;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private GradePointService pointService;
	
	@Autowired
	private GradePointDao gradePointDao;
	
	@Autowired
	private HttpServletRequest req;
	
	@Autowired
	private GradePointDto pointDto;
	
	@Override
	public void JoinWithFile(UsersDto usersDto, List<MultipartFile> user_file) throws IllegalStateException, IOException {
		
		//회원가입
		int user_no = sqlSession.selectOne("users.getSequence");
		
		usersDto.setPw(encoder.encode(usersDto.getPw()));
		usersDto.setUser_no(user_no);
		sqlSession.insert("users.join", usersDto);
		
		if(!user_file.get(0).isEmpty()) {
		
		//파일등록
		List<UserFileDto> list = new ArrayList<>();
		
		for(MultipartFile mf : user_file) { //올린 파일 개수만큼 반복
//			int board_file_no = boardDao.getsequence();
			if(!user_file.isEmpty()) {
				list.add(UserFileDto.builder()
												.user_no(user_no)
												.file_upload_name(mf.getOriginalFilename())
												.file_save_name(UUID.randomUUID().toString())
												.file_type(mf.getContentType())
												.file_size(mf.getSize())
												.build());
			}
		}
		
		//파일 저장(물리)			
		File dir = new File("D:/upload/kh2b");
		dir.mkdirs();
			
			for(int i = 0; i < list.size(); i++) {	
				UserFileDto userFileDto = list.get(i);//DB에 저장할 파일의 정보 객체
				MultipartFile file = user_file.get(i);//하드디스크에 저장할 실제 파일 객체
				
				File target = new File(dir, userFileDto.getFile_save_name()); //파일을 새로 생성하고 save_name을 가져와 그 이름으로
				file.transferTo(target); //실제파일저장
				
				userFileDao.fileUpload(userFileDto); //DB저장
			}
			
		}
		//회원 가입 포인트
		String id = req.getParameter("id");
		usersDto.setId(id);

		pointDto.setUsers_no(user_no);

		gradePointDao.giveJoinPoint(user_no);
			
	}
	
	@Override
	public ResponseEntity<ByteArrayResource> getImg(int user_no) throws Exception {
		
		//board_file_no에 대한 파일정보를 가져온다
		UserFileDto dto = userFileDao.getFile(user_no);

		//directory의 위치에 있는 저장이름으로 파일을 찾아서 불러온 뒤 반환
		//실제 파일을 읽어들인다. (폴더, 파일명)
		File dir = new File("D:/upload/kh2b");	
		File file = new File(dir, String.valueOf(dto.getFile_save_name()));
		
		byte[] data = FileUtils.readFileToByteArray(file); //파일을 바이트배열로 변환
		
		//헤더 설정 및 전송
		ByteArrayResource resource = new ByteArrayResource(data);
		
		//읽어들인 내용을 사용자에게 전송한다.
		return ResponseEntity.ok()
				//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(dto.getFile_size())
				.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
				.header(HttpHeaders.CONTENT_DISPOSITION, userFileDao.makeDispositionString(dto))
				.body(resource);				
				
	}

	
	@Override
	public void ProfileEdit(List<MultipartFile> user_file) throws IllegalStateException, IOException {
		
		String id = (String) req.getSession().getAttribute("id");
		int user_no = sqlSession.selectOne("users.get_users_no", id);
		
		//파일 삭제
		userFileDao.delete(user_no);
		
		//파일등록
		List<UserFileDto> list = new ArrayList<>();
		
		for(MultipartFile mf : user_file) { //올린 파일 개수만큼 반복
			if(!user_file.isEmpty()) {
				list.add(UserFileDto.builder()
												.user_no(user_no)
												.file_upload_name(mf.getOriginalFilename())
												.file_save_name(UUID.randomUUID().toString())
												.file_type(mf.getContentType())
												.file_size(mf.getSize())
												.build());
			}
		}
		//파일 저장(물리)			
		File dir = new File("D:/upload/kh2b");
		dir.mkdirs();
			
			for(int i = 0; i < list.size(); i++) {	
				UserFileDto userFileDto = list.get(i);//DB에 저장할 파일의 정보 객체
				MultipartFile file = user_file.get(i);//하드디스크에 저장할 실제 파일 객체
				
				File target = new File(dir, userFileDto.getFile_save_name()); //파일을 새로 생성하고 save_name을 가져와 그 이름으로
				file.transferTo(target); //실제파일저장
				
				userFileDao.fileUpload(userFileDto);
			}
		
	}
	
	//프로필 삭제 (기본이미지)
	@Override
	public void ProfileDelete(int user_no){
		
		userFileDao.delete(user_no);
		
	}
}
