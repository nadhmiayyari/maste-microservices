package com.app.accounts.service;

import com.app.accounts.constants.AccountsConstants;
import com.app.accounts.dto.AccountsDto;
import com.app.accounts.dto.CustomerDto;
import com.app.accounts.exceptions.CustomerAlreadyExistsException;
import com.app.accounts.exceptions.ResourceNotFoundException;
import com.app.accounts.mapper.AccountsMapper;
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
    public CustomerDto fetchAccounts(String mobileNumber) {
            Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("customer","mobile Number",mobileNumber)
        );
           Accounts account =
                   accountsRepository.findByCustomerId(customer.getCustomerId())
                           .orElseThrow(
                ()->new ResourceNotFoundException("Account","mobile Number",customer.
                        getCustomerId().toString())
        );

             CustomerDto customerDto = CustomerMapper.toDto(customer, new CustomerDto());
            customerDto.setAccountsDto(AccountsMapper.accountToDto(account,new AccountsDto()));
            return customerDto;
    }

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("customer already exists");
        }
        Customer customer = CustomerMapper.toCustomer(new Customer(),customerDto);
        Customer saved = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(saved));
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto dto = customerDto.getAccountsDto();
        if(dto!=null){
            Accounts account = accountsRepository.findById(dto.getAccountNumber())
                    .orElseThrow(()->new ResourceNotFoundException("Account","AccountNumber",dto.getAccountNumber().toString()));

            AccountsMapper.toAccounts(dto,account);
            account = accountsRepository.save(account);

            Long customerId= account.getCustomerId();

            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(()->new ResourceNotFoundException("Customer","customer Id",customerId.toString()));

            CustomerMapper.toCustomer(customer,customerDto);

            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException("customer","mobile Number",mobileNumber));

        accountsRepository.deleteAccountsByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;

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
