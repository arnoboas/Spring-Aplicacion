package com.example.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2582289195430057769L;


	


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(descripcion, id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(descripcion, other.descripcion) && id == other.id && Objects.equals(name, other.name);
	}


	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", descripcion=" + descripcion + "]";
	}


	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy = "native")
	private long id;
	
	@Column
	private String name;
	@Column
	private String descripcion;
	
	
	
}
