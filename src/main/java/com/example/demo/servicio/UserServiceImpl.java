package com.example.demo.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		
		Optional <User> userFound =userRepository.findByUsername(user.getUsername());
		
		if(userFound.isPresent()) {
			
			throw new Exception("Username no disponible");
		}
		
		return true;
		
	}
	
	private boolean checkPasswordValid(User user) throws Exception {
	
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Passwrod y ConfirmPassword no son iguales");
		}

		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		
		if(checkUsernameAvailable(user) && checkPasswordValid(user)) {
			
			user=userRepository.save(user);
		}
		
		return user;
	}

}
