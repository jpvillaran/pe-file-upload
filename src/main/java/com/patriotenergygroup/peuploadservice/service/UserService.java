package com.patriotenergygroup.peuploadservice.service;

import com.patriotenergygroup.peuploadservice.model.User;

public interface UserService {
	User findUserByUsername(String username);
	void saveUser(User user);
}
