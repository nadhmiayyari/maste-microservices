package com.app.accounts.service;

import com.app.accounts.dto.CustomerDto;

public interface IAccountsService {


    CustomerDto fetchAccounts(String mobileNumber);

    void createAccount(CustomerDto customerDto);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);

}
