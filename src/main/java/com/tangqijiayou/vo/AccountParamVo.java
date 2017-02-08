package com.tangqijiayou.vo;

public class AccountParamVo {

	/** 昵称 */
	private String nickName;

	/** 真实姓名 */
	private String realName;

	/** 头像地址 */
	private String logoUrl;

	/** 邮箱地址 */
	private String email;

	/** 性别 1为男生， 2为女生，3保密 */
	private Integer sex;

	/** 身份证 */
	private String idCard;

	/** 民族 */
	private String nation;

	/** 籍贯 */
	private String nativePlace;

	/** 婚姻状态 1未婚， 2已婚 */
	private int marriage;

	/** 家庭地址 */
	private String familyAddress;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public int getMarriage() {
		return marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	
}