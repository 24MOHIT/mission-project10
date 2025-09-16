package com.rays.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rays.common.BaseDTO;

@Entity
@Table(name = "st_role")
public class RoleDTO extends BaseDTO {

	public static String ACTIVE = "Active";
	public static String INACTIVE = "Inactive";

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "status", length = 15)
	private String status = ACTIVE;
	
	@Column(name = "role_name", length = 50)
	private String roleName;

	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValue() {
		return name;
	}

	@Override
	public String getUniqueKey() {
		return "name";
	}

	@Override
	public String getUniqueValue() {
		return name;
	}

	@Override
	public String getLabel() {
		return "Name";
	}

}