package com.tangqijiayou.vo;


import com.tangqijiayou.model.Account;

public class AccountResultVo {
	/** 主键    这里约定统一下主键的数值类型都用Long */
	private Long id;
	
	/** 电话 */
	private String phone;
	
	/** 昵称 */
	private String nickName;
	
	/** 真实姓名 */
	private String realName;
	
	/** 头像地址 */
	private String logoUrl;
	
	/** 邮箱地址 */
	private String email;
	
	/** 账号状态  账号状态 @see AccountStatusEnum*/
	private Integer accountStatus;
	
	/** 角色，这里用作权限控制  @see RoleEnum*/
	private String role;
	
	/** 身份证 */
	private String idCard;
	
	/** 认证状态 0未认证， 1已认证*/
	private int auth;

	/** 删除标志 1为删除*/
    private Integer del;
    
    public AccountResultVo(){
    	
    }
    
    public AccountResultVo(Account account){
		super();
		this.id = account.getId();
		this.phone = account.getPhone();
		this.nickName = account.getNickName();
		this.realName = account.getRealName();
		this.logoUrl = account.getLogoUrl();
		this.email = account.getEmail();
		this.accountStatus = account.getAccountStatus();
		this.role = account.getRole();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}
    
    
    
	
}