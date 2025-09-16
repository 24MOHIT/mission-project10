package com.rays.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;
import com.rays.dto.MarksheetDTO;

public class MarksheetForm extends BaseForm {

	@NotNull
	protected String rollNo = null;

	protected String name = null;

	@NotNull
	protected Integer physics;

	@NotNull
	protected Integer chemistry;

	@NotNull
	protected Integer maths;

	@NotNull
	protected Long studentId;

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhysics() {
		return physics;
	}

	public void setPhysics(Integer physics) {
		this.physics = physics;
	}

	public Integer getChemistry() {
		return chemistry;
	}

	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}

	public Integer getMaths() {
		return maths;
	}

	public void setMaths(Integer maths) {
		this.maths = maths;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	@Override
	public BaseDTO getDto() {

		MarksheetDTO dto = initDTO(new MarksheetDTO());
		dto.setRollNo(rollNo);
		dto.setName(name);
		dto.setPhysics(physics);
		dto.setChemistry(chemistry);
		dto.setMaths(maths);
		dto.setStudentId(studentId);

		return dto;
	}
}
