package com.common.module.user.controller;

import java.util.Collection;

import com.common.module.user.dao.UserDAO;
import com.common.module.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserDAO userDAO;

	@GetMapping("/list")
	public Collection<User> list() {
		return userDAO.getAll();
	}

}
