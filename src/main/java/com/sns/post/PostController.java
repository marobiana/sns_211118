package com.sns.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sns.post.bo.PostBO;
import com.sns.post.model.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	@Autowired
	private PostBO postBO;

	// 글 목록 테스트
	@RequestMapping("/post_list_view")
	public String postListView(Model model) {
		List<Post> postList = postBO.getPostList();
		model.addAttribute("postList", postList);
		
		return "test/example";
	}
}
