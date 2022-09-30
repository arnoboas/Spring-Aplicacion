package com.example.demo.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePasswordForm {

	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(confirmPassword, currentPassword, id, newPassword);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangePasswordForm other = (ChangePasswordForm) obj;
		return Objects.equals(confirmPassword, other.confirmPassword)
				&& Objects.equals(currentPassword, other.currentPassword) && Objects.equals(id, other.id)
				&& Objects.equals(newPassword, other.newPassword);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
	
	@Override
	public String toString() {
		return "ChangePasswordForm [id=" + id + ", currentPassword=" + currentPassword + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + "]";
	}




	@NotNull Long id;
	@NotBlank(message="Current Password must not be blank")
	private String currentPassword;

	@NotBlank(message="Nuevo password no debe estar en blanco")
	private String newPassword;

	@NotBlank(message="Confirm Password no debe estar en blanco")
	private String confirmPassword;

	public ChangePasswordForm() { }
	public ChangePasswordForm(Long id) {this.id=id;}

}
