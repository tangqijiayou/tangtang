package com.tangqijiayou.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "account")
public class Account extends BeanBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** 主键 这里约定统一下主键的数值类型都用Long */
	private Long id;

	/** 无线用户*/
	@Column(name = "user_id", columnDefinition = "varchar(255) default '' ")
	private String userId;

	/** 电话 */
	@Column(name = "phone", columnDefinition = "varchar(255) default '' ")
	private String phone;

	/** 昵称 */
	@Column(name = "nick_name", columnDefinition = "varchar(255) default '' ")
	private String nickName;

	/** 密码 */
	@Column(name = "password", columnDefinition = "varchar(255) default '' ")
	private String password;

	/** 真实姓名 */
	@Column(name = "real_name", columnDefinition = "varchar(255) default '' ")
	private String realName;

	/** 头像地址 */
	@Column(name = "logo_url", columnDefinition = "varchar(255) default '' ")
	private String logoUrl;

	/** 邮箱地址 */
	@Column(name = "email", columnDefinition = "varchar(255) default '' ")
	private String email;

	/** 学校 */
	@Column(name = "school", columnDefinition = "varchar(255) default '' ")
	private String school;

	/** 班级 */
	@Column(name = "clazz", columnDefinition = "varchar(255) default '' ")
	private String clazz;

	/** 学号 */
	@Column(name = "student_id", columnDefinition = "varchar(255) default '' ")
	private String studentId;

	/** 账号状态 账号状态 @see AccountStatusEnum */
	@Column(name = "account_status", columnDefinition = "int not null default 1")
	private Integer accountStatus;

	/** 角色，这里用作权限控制 @see RoleEnum */
	@Column(name = "role", columnDefinition = "varchar(255) default '' ")
	private String role;

	/** 性别 1为男生， 2为女生，3保密 */
	@Column(name = "sex", columnDefinition = "int not null default 1")
	private Integer sex;

	/** 创建时间 */
	@Column(name = "create_time")
	@org.hibernate.annotations.CreationTimestamp
	public Timestamp createTime;

	/** 最后更新时间 */
	@Column(name = "update_time")
	@org.hibernate.annotations.UpdateTimestamp
	public Timestamp updateTime;

	public Account() {

	}

	/***
	 * 用于新增用户
	 * 
	 * */
	public Account(String userId, String phone, String logoUrl, String nickname, int timeNumber, String school, String clazz, String studentId) {
		super();
		this.setUserId(userId);
		this.setPhone(phone);
		this.setLogoUrl(logoUrl);
		this.setNickName(nickname);
		this.setAccountStatus(AccountStatusEnum.Enable.value);
		this.setPassword(userId);
		this.setEmail("");
		this.setRole(RoleEnum.user.name);
		this.setSex(SexEnum.Secrecy.value);
		this.setSchool(school);
		this.setClazz(clazz);
		this.setStudentId(studentId);
	}

	public static enum AccountStatusEnum {
		Enable(1, "正常"), Disenable(2, "冻结");

		public final int value;
		public final String name;

		AccountStatusEnum(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static final AccountStatusEnum accountStatusEnum(int state) {
			switch (state) {
			case 1:
				return Enable;
			case 2:
				return Disenable;
			default:
				throw new IllegalArgumentException("Illegal state value:'" + state + "'");
			}
		}
	}

	public static enum SexEnum {
		Man(1, "男生"), Woman(2, "女生"), Secrecy(3, "保密");

		public final int value;
		public final String name;

		SexEnum(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static final SexEnum sexEnum(int state) {
			switch (state) {
				case 1:
					return Man;
				case 2:
					return Woman;
				case 3:
					return Secrecy;
				default:
					throw new IllegalArgumentException("Illegal state value:'" + state + "'");
			}
		}
	}
	

	public static enum RoleEnum {
		user(1, "user"), admin(2, "admin");

		public final int value;
		public final String name;

		RoleEnum(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static final RoleEnum roleEnum(int state) {
			switch (state) {
			case 1:
				return user;
			case 2:
				return admin;
			default:
				throw new IllegalArgumentException("Illegal state value:'" + state + "'");
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}