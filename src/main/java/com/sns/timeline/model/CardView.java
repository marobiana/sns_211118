package com.sns.timeline.model;

import java.util.List;

import com.sns.comment.model.Comment;
import com.sns.comment.model.CommentView;
import com.sns.post.model.Post;
import com.sns.user.model.User;

// 하나의 카드를 의미
public class CardView {
	// 글 하나
	private Post post;
	// 댓글들
	private List<CommentView> commentList;
//	// 좋아요들
//	private List<Like> likeList;
	
	// 글쓴이 정보 => 글에 대한 
	private User user;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<CommentView> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentView> commentList) {
		this.commentList = commentList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
