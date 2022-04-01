package com.sns.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	// 실제 이미지가 저장될 경로
	// 프로젝트 밑에 images 폴더를 만들어 놓아야 한다!!!!
	public final static String FILE_UPLOAD_PATH = "D:\\신보람\\웹_211118\\6_spring_project\\quiz_sns\\quiz_workspace\\images/";
	
	public String saveFile(String loginId, MultipartFile file) throws IOException {
		// 파일 디렉토리 경로 예: marobiana_1620995857660/sun.png    
		// 파일명이 겹치지 않게 현재시간을 경로에 붙여준다.
		String directoryName = loginId + "_" + System.currentTimeMillis() + "/";
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(filePath);
		// directory.mkdir() -> 파일을 업로드 할 filePath 경로에 폴더(디렉토리) 생성을 한다. 
		if (directory.mkdir() == false) {
			return null; // 디렉토리 생성 실패시 null 리턴
		}
		
		// 파일 업로드: byte 단위로 업로드한다.
		byte[] bytes = file.getBytes();
		Path path = Paths.get(filePath + file.getOriginalFilename()); // originalFilename은 input에 올린 파일명이다.
		Files.write(path, bytes);
		
		// 이미지 URL path를 리턴한다. (WebMvcConfig에서 매핑한 이미지 path)
		//   예) http://localhost/images/marobiana_1620995857660/sun.png
		return "/images/" + directoryName + file.getOriginalFilename();
	}
	
	public void deleteFile(String imagePath) throws IOException {
		// imagePath의 /images/marobiana_16456453342/sun.png 에서 /images/ 를 제거한 path를 실제 저장경로 뒤에 붙인다.
		// D:\\신보람\\web_211015\\6_spring-project\\memo\\workspace\\images/         /images/marobiana_16456453342/sun.png
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		if (Files.exists(path)) { // 이미지 파일이 있으면 삭제
			Files.delete(path);
		}
		
		// 디렉토리(폴더) 삭제
		path = path.getParent();
		if (Files.exists(path)) {
			Files.delete(path);
		}
	}
}
