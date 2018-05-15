package com.example.demo.contreller.sysManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class SysManagerController {

	@RequestMapping
	public String index() {
		return "index";
	}

	@RequestMapping("{page}")
	public String getPage(@PathVariable String page) {
		return page;
	}
}
