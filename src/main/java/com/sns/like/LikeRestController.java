package com.sns.like;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeRestController {
	
	@RequestMapping("/like/{postId}")
	public Map<String, Object> like(
			@PathVariable int postId, 
			HttpServletRequest request) {
		
		// TODO ...
	}
}






