package com.iacsd.demo.service.Impl;

import com.iacsd.demo.exception.ExceptionPool;
import com.iacsd.demo.model.Account;
import com.iacsd.demo.repository.AccountRepo;
import com.iacsd.demo.security.JwtUtil;
import com.iacsd.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private AccountRepo accountRepo;
    @Autowired private JwtUtil jwtUtil;

    @Override
    public Account getLoggedInUserAccount() {
        return accountRepo.getAccountByUserUid(jwtUtil.getUid()).orElseThrow(ExceptionPool.REQ_INVALID);
    }

    @Override
    public Long getLoggedInUserAccountId() {
        return accountRepo.getAccountIdByUserUid(jwtUtil.getUid()).orElseThrow(ExceptionPool.REQ_INVALID);
    }
}
