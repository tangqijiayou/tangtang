package com.tangqijiayou.vo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class LoginParamVo {
	/** 手机号 */
	@Size(min=11, max=11, message = "请输入真确的手机号")
	private String phone;
	/** 密码 */
	@NotBlank
	private String password;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}