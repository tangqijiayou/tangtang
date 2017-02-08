package com.tangqijiayou.repository;

import com.tangqijiayou.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author merry
 *
 */
public interface IAccountRepository extends JpaRepository<Account, Long>{
	/**
	 * 根据手机号查询用户表
	 * 
	 * @param phone
	 * @return
	 */
	public Account findByPhoneAndUserId(final String phone, final String userId);
	/**
	 * 根据手机号查询用户表
	 *
	 * @param phone
	 * @return
	 */
	public Account findByPhone(final String phone);
	/**
	 * 新增或更新用户
	 * 
	 * @param account
	 * @return
	 */
	public Account save(final Account account);

	/**
	 * 删除用户
	 *
	 * @param account
	 * @return
	 */
	public void delete(final Account account);

	/**
	 * 无条件分页查询用户
	 *
	 * @param pageable
	 * @return
	 */
	public Page<Account> findAll(Pageable pageable);

	/**
	 * 动态条件分页查询用户
	 *
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public Page<Account> findAll(Specification<Account> spec, Pageable pageable);
	
}
