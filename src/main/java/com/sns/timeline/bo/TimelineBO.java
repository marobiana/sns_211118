package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.model.CommentView;
import com.sns.like.bo.LikeBO;
import com.sns.post.bo.PostBO;
import com.sns.post.model.Post;
import com.sns.timeline.model.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.model.User;

@Service
public class TimelineBO {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private LikeBO likeBO;
	
	// 타임라인 화면의 경우 비로그인일 때도 보여져야 하므로 Integer userId
	public List<CardView> generateCardViewList(Integer userId) {
		List<CardView> cardViewList = new ArrayList<>();
		
		// 글 List 가져온다.
		List<Post> postList = postBO.getPostList();
		for (Post post : postList) {
			CardView card = new CardView();
			
			// 글 정보
			card.setPost(post);

			// 글쓴이 정보
			User user = userBO.getUserByUserId(post.getUserId());
			card.setUser(user);
			
			// 댓글들 정보
			List<CommentView> commentList = commentBO.generateCommentViewList(post.getId());
			card.setCommentList(commentList);
			
			// 좋아요 카운트
			card.setLikeCount(likeBO.getLikeCountByPostId(post.getId()));
			
			// 지금 좋아요 눌렀는지 여부
			card.setFilledLike(likeBO.existLike(post.getId(), userId));
			
			cardViewList.add(card);
		}
		
		return cardViewList;
	}
}






