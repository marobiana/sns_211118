package com.sns.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sns.post.model.Post;

@Repository
public interface PostDAO {
	public List<Post> selectPostList();
	
	public Post selectPostByIdAndUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public void insertPost(
			@Param("userId") int userId, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	
	public void deletePostByIdAndUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
}
