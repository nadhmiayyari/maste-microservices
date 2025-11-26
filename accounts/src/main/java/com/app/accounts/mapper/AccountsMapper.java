package com.app.accounts.mapper;

import com.app.accounts.dto.AccountsDto;
import com.app.accounts.model.Accounts;

public class AccountsMapper {



    public static AccountsDto accountToDto(Accounts account, AccountsDto accountsDto){
        accountsDto.setAccountNumber(account.getAccountNumber());
        accountsDto.setAccountType(account.getAccountType());
        accountsDto.setBranchAddress(account.getBranchAddress());
        return accountsDto;
    }


    public static Accounts toAccounts(AccountsDto accountsDto,Accounts accounts){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
