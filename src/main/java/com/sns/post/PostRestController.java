package com.sns.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostBO postBO;
	
	/**
	 * 글쓰기
	 * @param content
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("content") String content,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<>();

		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
		if (userId == null) {
			result.put("result", "error");
			result.put("errorMessage", "로그인을 해주세요.");
			return result;
		}
		
		postBO.addPost(userId, userLoginId, content, file);
		
		result.put("result", "success");
		return result;
	}
	
	/**
	 * 글 삭제
	 * @param postId
	 * @param session
	 * @return
	 */
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			logger.error("[post delete] not found login session. postId:{}", postId);
			result.put("result", "error");
			result.put("error_message", "로그인을 다시해주세요.");
			return result;
		}
		
		// delete db
		postBO.deletePost(postId, userId);
		
		result.put("result", "success");
		return result;
	}
}




