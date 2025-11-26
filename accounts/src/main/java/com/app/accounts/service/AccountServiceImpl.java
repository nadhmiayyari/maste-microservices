package com.app.accounts.service;

import com.app.accounts.constants.AccountsConstants;
import com.app.accounts.dto.CustomerDto;
import com.app.accounts.exceptions.CustomerAlreadyExistsException;
import com.app.accounts.mapper.CustomerMapper;
import com.app.accounts.model.Accounts;
import com.app.accounts.model.Customer;
import com.app.accounts.repository.AccountsRepository;
import com.app.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@AllArgsConstructor
@Service
public class AccountServiceImpl implements IAccountsService{

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("customer already exists");
        }
        Customer customer = CustomerMapper.toCustomer(new Customer(),customerDto);
        Customer  saved = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(saved));
    }


    private Accounts createNewAccount(Customer customer ){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000L+ new Random().nextInt(9000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
