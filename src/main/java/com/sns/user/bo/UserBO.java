package com.sns.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.user.dao.UserDAO;
import com.sns.user.model.User;

@Service
public class UserBO {
	@Autowired
	private UserDAO userDAO;
	
	public int existLoginId(String loginId) {
		return userDAO.existLoginId(loginId);
	}
	
	public User getUserByLoginIdAndPassword(String loginId, String password) {
		return userDAO.selectUserByLoginIdAndPassword(loginId, password);
	}
	
	public User getUserByUserId(int userId) {
		return userDAO.selectUserByUserId(userId);
	}
	
	public int insertUser(String loginId, String password, String name, String email) {
		return userDAO.insertUser(loginId, password, name, email);
	}
}
