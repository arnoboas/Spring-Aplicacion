package com.example.demo.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.UsernameOrIdNotFound;
import com.example.demo.dto.ChangePasswordForm;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
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
			
			//Encriptar contrasena de nuevo usuarip antes de que se guarde
			String encodePassword =bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			
			user=userRepository.save(user);
		}
		
		return user;
	}

	@Override
	public User getUserById(long id) throws UsernameOrIdNotFound {

		User user = userRepository.findById(id).orElseThrow(() -> new UsernameOrIdNotFound("El id o el usuario no existe"));
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
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws UsernameOrIdNotFound {
		// TODO Auto-generated method stub
		
		/*User user = userRepository.findById(id)
				.orElseThrow(() -> new Exception("Usuario no existe para eliminar"
												+this.getClass().getName()));
		*/
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameOrIdNotFound("Usuario no existe para eliminar"));
		
		
		userRepository.delete(user);
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		
		/*User storedUser = userRepository
				.findById( form.getId() )
				.orElseThrow(() -> new Exception("El usuario no se encuentra en el ChangePassword-"+this.getClass().getName()));
		*/
		
		User user=getUserById(form.getId());
		
		if( !isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Current Password Invalido.");
		}
		
		if ( user.getPassword().equals(form.getNewPassword())) {
			throw new Exception("New Password debe ser diferente del Current Password!");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("New Password y Confirm Password no son iguales!");
		}


		
		//Codificar passwrod con Spring security antes de guardar el nuevo password que cambio el usuario
		String encodePassword =bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);
		return userRepository.save(user);
		


	
	}
	
	public boolean isLoggedUserADMIN(){

		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = null;
		//Object roles = null; 
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		
			loggedUser.getAuthorities().stream()
					.filter(x -> "ADMIN".equals(x.getAuthority() ))      
					.findFirst().orElse(null); //loggedUser = null;
		}
		
		return loggedUser != null ?true :false;
		
	}



}
