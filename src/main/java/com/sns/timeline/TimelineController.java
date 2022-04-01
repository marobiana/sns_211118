package com.sns.timeline;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.model.CardView;

@RequestMapping("/timeline")
@Controller
public class TimelineController {
	
	@Autowired
	private TimelineBO timelineBO;
	
	/**
	 * 타임라인 화면
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/timeline_view")
	public String timelineView(Model model, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		
		List<CardView> cardViewList = timelineBO.generateCardViewList(userId);
		
		model.addAttribute("cardViewList", cardViewList);
		model.addAttribute("viewName", "timeline/timeline");
		return "template/layout";
	}
}
