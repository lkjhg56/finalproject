package com.kh.finalproject.repository;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.entity.UserQuestionMultiResultDto;
import com.kh.finalproject.entity.UserQuestionResultDto;

@Repository
public class UploadQuestionDaoImple implements UploadQuestionDao{
	@Autowired
	private SqlSession sqlSession;
//	시퀀스 미리뽑기
	public int questionSequece() {
		return sqlSession.selectOne("question.sequence"); 
	}
	public int userSequence() {
		return sqlSession.selectOne("question.userSequence");
		
	}	
	@Override
	public int questionResultSequece() {
		return sqlSession.selectOne("question.questionSequence");
	}
	//전체 문제 개수 세기
	@Override
	public int questionCount() {
		return sqlSession.selectOne("question.questionCount");
	}
	//map으로 문제 리스트 불러오기
	@Override
	public List<UploadQuestionDto> mapList(Map<String, Integer> param){
		return sqlSession.selectList("question.questionNavRownum",param);
	}
	//문제를 DB에 업로드
	public void upload(UploadQuestionDto uploadQuestionDto) {
		sqlSession.insert("question.upload_sub", uploadQuestionDto);//user_costom_question 등록
		sqlSession.insert("question.upload", uploadQuestionDto);// question 등록
	}
	//문제 이미지 파일 업로드
	@Override
	public void fileUpload(UploadQuestionFileDto uploadQuestionFileDto) {
		sqlSession.insert("question.upload_file", uploadQuestionFileDto);	
	}
	//이미지 업로드시 ResponseEntity 헤더에 추가할 내용
	@Override
	public String makeDispositionString(UploadQuestionFileDto uploadQuestionFileDto) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("attachment;");
		buffer.append("filename=");
		buffer.append("\"");
		buffer.append(URLEncoder.encode(uploadQuestionFileDto.getFile_upload_name(), "UTF-8"));
		buffer.append("\"");
		return buffer.toString();
	}
	//문제 수정
	@Override
	public void updateQustion(UploadQuestionDto uploadQuestionDto) {
		sqlSession.update("question.updateCustom",uploadQuestionDto);
		sqlSession.update("question.updateQuestion",uploadQuestionDto);
	}
	//문제 이미지 수정
	public void updateFile(UploadQuestionFileDto uploadQuestionFileDto) {		
		sqlSession.update("question.updateFile",uploadQuestionFileDto);
	}
	//session의 id로 사용자 번호 조회
	@Override
	public int getUserNo(String id) {
		return sqlSession.selectOne("question.getNo",id);
	}
	//전체 리스트 조회를 위한 것
	@Override
	public List<UploadQuestionDto> getList() {
		return sqlSession.selectList("question.getList");
	}
	//이미지 파일과 합쳐서 나오는 것
	@Override
	public List<UploadQuestionDto> getListWithImage() {
		return sqlSession.selectList("question.getListWithImage");
	}
	//이미지 파일과 합쳐서 나오는 것(question_no 조건)
	@Override
	public List<UploadQuestionDto> getListWithImageByNumber(int question_no){
		return sqlSession.selectList("question.getListWithImageByNumber",question_no);
	}
	//문제 파일 조회(question_no)
	@Override
	public UploadQuestionFileDto getFile(int question_no) {
		return sqlSession.selectOne("question.getFile",question_no);		
	}
	//문제 파일 조회(question_file_no)
	@Override
	public UploadQuestionFileDto getFile3(int question_file_no) {
		return sqlSession.selectOne("question.getFile2",question_file_no);		
	}
	//문제 파일 조회(리스트)
	@Override
	public List<UploadQuestionFileDto> getFile2(int question_no) {
		return sqlSession.selectList("question.getFile",question_no);	
	}
	//문제 삭제(하나의 question table 삭제 시 file, result가 날아가도록 함.)
	//user_custom_question은 별도로 지워줘야함.
	public void fileDelete2(int question_no, int user_custom_question_no) {	
		sqlSession.delete("question.deleteUser",user_custom_question_no);
		sqlSession.delete("question.deleteQuestion",question_no);
	}
	//개별 파일 삭제
	@Override
	public void deleteFile(int question_file_no) {
		sqlSession.delete("question.deleteFile",question_file_no);
	}
	//시간 포멧 설정
	public String timeCheck() {
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
		Date time = new Date();		
		String time1 = format.format(time);
		return time1;
	}
	//사용자가 맞았는지 DB에서 조회
	@Override
	public int question_true(int question_no) {		
		return sqlSession.selectOne("question.userResultTrue",question_no);
	}
	//사용자가 틀렸는지 DB에서 조회
	@Override
	public int question_false(int question_no) {		
		return sqlSession.selectOne("question.userResultFalse",question_no);
	}
	//join 검색(user, user_custom_question, question)
	@Override
	public UploadQuestionDto question_all(int question_no) {		
		return sqlSession.selectOne("question.getTotal", question_no);
	}
	@Override
	public List<UploadQuestionDto> question_user_all() {
		return sqlSession.selectList("question.getTotal2");
	}
	//사용자가 해결한 문제 결과를 DB에 저장
	@Override
	public void insert_result(UserQuestionResultDto userQuestionResultDto) {
		sqlSession.insert("question.insert_result",userQuestionResultDto);		
	}
	//사용자가 해결한 다중 문제 결과를 DB에 저장
	@Override
	public void insert_multi_result(UserQuestionMultiResultDto userQuestionMultiResultDto) {
		sqlSession.insert("question.insert_multi_result",userQuestionMultiResultDto);
	}
	//사용자가 한 문제에 대해 해결한 순위 조회
	@Override
	public UserQuestionResultDto userPriority(int question_no, int result_no) {
		UserQuestionResultDto userQuestionResultDto = UserQuestionResultDto.builder()
				.question_no(question_no)
				.result_no(result_no)
				.build();
		return sqlSession.selectOne("question.userPriority", userQuestionResultDto);
	}
	//사용자가 다중 문제 해결시 각 문제에 대한 정답을 DB에서 조회
	@Override
	public UploadQuestionDto isCorrect(int question_no) {
		return sqlSession.selectOne("question.getOne", question_no);
	}
	//유저 문제 해결 후 3point 제공
	@Override
	public void givePointforSolving(int user_no) {
		sqlSession.insert("grade_point.userQuestionSolve", user_no);
	}
	//정답률 계산
	@Override
	public void correct_ratio(int question_no) {
		UploadQuestionDto uploadQuestionDto = UploadQuestionDto.builder()
			.question_no(question_no)
			.correct_ratio(sqlSession.selectOne("question.correctRatio",question_no))					
			.build();
		sqlSession.selectOne("question.updateRatio",uploadQuestionDto);
	}
	//조회수 증가
	@Override
	public void update_read_count(int question_no) {
		sqlSession.update("question.upReadCount",question_no);
		
	}

	}

