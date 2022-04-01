package com.sns.post.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.dao.PostDAO;
import com.sns.post.model.Post;

@Service
public class PostBO {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
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
	
	public void deletePost(int postId, int userId) {
		// select post
		Post post = postDAO.selectPostByIdAndUserId(postId, userId);
		
		// post null 검사 => null이면 logger, 0 return
		if (post == null) {
			logger.error("[delete post] 삭제할 게시물이 없습니다. postId:{}", postId);
			return;
		}
		
		// 이미지 삭제 
		if (post.getImagePath() != null) {
			try {
				fileManagerService.deleteFile(post.getImagePath());
			} catch (IOException e) {
				logger.error("[delete post] 이미지 삭제 실패, postId:{}, path:{}", postId, post.getImagePath());
			}
		}
		
		// 글 삭제 byPostIdUserId
		postDAO.deletePostByIdAndUserId(postId, userId);
		
		// 댓글들 삭제 byPostId
		commentBO.deleteCommentsByPostId(postId);
		
		// 좋아요들 삭제 byPostId
		likeBO.deleteLikeByPostId(postId);
	}
}




