package com.sns.post.bo;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.common.FileManagerService;
import com.sns.post.dao.PostDAO;
import com.sns.post.model.Post;

@Service
public class PostBO {
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private FileManagerService fileManagerService;

	public List<Post> getPostList() {
		return postDAO.selectPostList();
	}
	
	public void addPost(int userId, String userLoginId, String content, MultipartFile file) {
		String imagePath = null;
		if (file != null) {
			try {
				imagePath = fileManagerService.saveFile(userLoginId, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		postDAO.insertPost(userId, content, imagePath);
	}
	
	public int deletePost(int postId, int userId) {
		// select post
		
		// post null 검사 => null이면 logger, 0 return
		
		// 이미지 삭제 
		
		// 글 삭제
		
		// 댓글들 삭제
		
		// 좋아요들 삭제 byPostId
		
		return 0;
	}
}




