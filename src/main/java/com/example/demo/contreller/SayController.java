package com.example.demo.contreller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("say")
public class SayController {
	@RequestMapping("hello")
	public String HelloWorld() {
		return "hello world from spring boot";
	}

	@RequestMapping("saySth/{sth}")
	public String say(@PathVariable String sth) {
		return sth;
	}
}
