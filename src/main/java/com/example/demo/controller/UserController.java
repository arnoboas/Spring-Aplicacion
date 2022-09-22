package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.servicio.UserService;

@Controller
public class UserController {
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@Autowired
	UserService userService;


	
	@GetMapping("/userForm")
	public String getUserForm(Model model) {

		model.addAttribute("userForm", new User());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab","active");
		
		return "user-form/user-view";
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model){
	//public String createUser(@ModelAttribute("userForm") User user, BindingResult result, ModelMap model){
			
		if(result.hasErrors()) {
			
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			
		}else {
			
			try {
				userService.createUser(user);
				model.addAttribute("userForm",new User());
				model.addAttribute("listTab","active");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles",roleRepository.findAll());	
			
			}
		}
		
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());
		
		return "user-form/user-view";
	}
	
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model,@PathVariable(name="id") Long id) throws Exception {
	
			User userToEdit=userService.getUserById(id);
		
			
			model.addAttribute("userForm", userToEdit);
			model.addAttribute("userList", userService.getAllUsers());
			model.addAttribute("roles",roleRepository.findAll());	
			model.addAttribute("formTab","active");
			model.addAttribute("editMode",true);
			
			return"user-form/user-view";
	}
	
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		
		if(result.hasErrors()) {
			
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode",true);
			
		}else {
			
			try {
				userService.updateUser(user);
				model.addAttribute("userForm",new User());
				model.addAttribute("listTab","active");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles",roleRepository.findAll());	
				model.addAttribute("editMode",true);
			}
		}
		
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());
		
		return "user-form/user-view";
		

	}
	
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
}
