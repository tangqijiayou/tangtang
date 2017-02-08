package com.tangqijiayou.service.impl;


import com.tangqijiayou.model.Account;
import com.tangqijiayou.repository.IAccountRepository;
import com.tangqijiayou.service.IAccountService;
import com.tangqijiayou.vo.AccountParamVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	private @Resource
	IAccountRepository accountRepository;


	@Override
	public Account findByPhoneAndUserId(final String phone, final String userId) {

		return accountRepository.findByPhoneAndUserId(phone, userId);
	}

	@Override
	public Account findByPhone(String phone) {
		return accountRepository.findByPhone(phone);
	}

	@Override
	public Account save(final Account account) {

		return accountRepository.save(account);
	}

	@Override
	public void delete(Account account) {
		accountRepository.delete(account);
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountRepository.findAll(pageable);
	}

	@Override
	public Page<Account> findAll(Pageable pageable, AccountParamVo accountVo) {
		Specification<Account> spec = getWhereClause(accountVo);
        return accountRepository.findAll(spec, pageable);
	}





    /**
     * 从vo对象中获取需要查询的条件
     *
     * @param accountVo
     * @return
     */
	private Specification<Account> getWhereClause(final AccountParamVo accountVo) {
		return (Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> list = new ArrayList<>();

			//开始判断需要筛选的条件，这里只示例了id，后面可以根据需求补全

//			if(accountVo.getId() != 0){
//				list.add(cb.equal(root.get("id").as(Integer.class), accountVo.getId()));
//			}

			Predicate[] p = new Predicate[list.size()];
			return cb.and(list.toArray(p));
        };
	}


}
