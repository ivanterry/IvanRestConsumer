package com.ivan.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ivan.common.model.User;

@Controller
@RequestMapping("/login")
public class JSONController {

	@RequestMapping(value = "{name}", method = RequestMethod.GET)
	public @ResponseBody
	User getLoginInJSON(@PathVariable String name) {

		User user = new User();
		user.setUser("test");
		user.setPass("secret");

		return user;
	}
	
}