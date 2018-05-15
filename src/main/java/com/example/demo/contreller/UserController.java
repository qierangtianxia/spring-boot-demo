package com.example.demo.contreller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("listAll")
	public Iterable<User> listAll() {
		Iterable<User> findAll = userRepository.findAll();
		return findAll;
	}

	@GetMapping("findUserById/{id}")
	public User findUserById(@PathVariable String id) {
		Integer idInt=Integer.parseInt(id);
		Optional<User> findById = userRepository.findById(idInt);
		return findById.get();
	}

	/**
	 * 
	 * @param user
	 * @return
	 * 
	 * 
	 * http://localhost:8088/user/addUser
	 * 
	 * {
			"name":"赵五",
			"age":26,
			"sex":"w"
		}
	 */
	@PostMapping("addUser")
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);

	}

	@GetMapping("delUser")
	public void delUserById(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	@PostMapping("updataUser")
	public User updataUser(@PathVariable User user) {
		return userRepository.save(user);
	}
}
