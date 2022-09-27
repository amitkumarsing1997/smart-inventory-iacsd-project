package com.iacsd.demo.service;

import com.iacsd.demo.model.Account;

public interface AccountService {
    Account getLoggedInUserAccount();
    Long getLoggedInUserAccountId();
}
