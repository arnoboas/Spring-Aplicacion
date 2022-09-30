package com.example.demo.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ChangePasswordForm;
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
	
		if(user.getConfirmPassword() ==null || user.getConfirmPassword().isEmpty()) {
			throw new Exception("Confirmar Password es Obligatorio");
		}
		
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

	@Override
	public User getUserById(long id) throws Exception {

		User user = userRepository.findById(id).orElseThrow(() -> new Exception("Usuario no existe"));
		return user;
	}

	@Override
	public User updateUser(User fromUser) throws Exception {

		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return userRepository.save(toUser);
	}
	
	/**
	 * Map everything but the password.
	 * @param from
	 * @param to
	 */
	
	protected void mapUser(User from,User to) {
		
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
		//to.setPassword(from.getPassword());
		

	}

	//@Override
	public void deleteUser(Long id) throws Exception {
		// TODO Auto-generated method stub
		
		/*User user = userRepository.findById(id)
				.orElseThrow(() -> new Exception("Usuario no existe para eliminar"
												+this.getClass().getName()));
		*/
		User user = userRepository.findById(id)
				.orElseThrow(() -> new Exception("Usuario no existe para eliminar"));
		
		
		userRepository.delete(user);
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		
		/*User storedUser = userRepository
				.findById( form.getId() )
				.orElseThrow(() -> new Exception("El usuario no se encuentra en el ChangePassword-"+this.getClass().getName()));
		*/
		
		User user=getUserById(form.getId());
		
		if( !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Current Password Invalido.");
		}
		
		if ( user.getPassword().equals(form.getNewPassword())) {
			throw new Exception("New Password debe ser diferente del Current Password!");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("New Password y Confirm Password no son iguales!");
		}
		
		user.setPassword(form.getNewPassword());
		return userRepository.save(user);
	
	}


	




}
