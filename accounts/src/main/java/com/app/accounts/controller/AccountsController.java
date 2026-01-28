package com.app.accounts.controller;


import com.app.accounts.constants.AccountsConstants;
import com.app.accounts.dto.CustomerDto;
import com.app.accounts.dto.ResponseDto;
import com.app.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path="/api",produces= {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {
    private IAccountsService accountService;


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.builder().statusCode(AccountsConstants.STATUS_201)
                        .statusMsg( AccountsConstants.MESSAGE_201).build());
}


    @GetMapping
    public ResponseEntity<CustomerDto> fetchCustomerById(@Valid @RequestParam
@Pattern(regexp = "(^$|[0-9]{10})",message="Mobile number must be 10 digits ")String mobileNumber
                                                    ){
       CustomerDto customerDto = accountService.fetchAccounts(mobileNumber);
       return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto){
         boolean isUpdated = accountService.updateAccount(customerDto);
         if(isUpdated){
             return ResponseEntity.status(HttpStatus.OK).body(ResponseDto
                     .builder()
                     .statusMsg(AccountsConstants.MESSAGE_200)
                             .statusCode(AccountsConstants.STATUS_200)
                     .build());
         }
         else {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto
                     .builder()
                     .statusMsg(AccountsConstants.MESSAGE_500)
                     .statusCode(AccountsConstants.STATUS_500)
                     .build());
         }
    }


    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccountsDetails(@RequestParam
                @Pattern(regexp = "(^$|[0-9]{10})",message="Mobile number must be 10 digits ")String mobileNumber){
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto
                    .builder()
                    .statusMsg(AccountsConstants.MESSAGE_200)
                    .statusCode(AccountsConstants.STATUS_200)
                    .build());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto
                    .builder()
                    .statusMsg(AccountsConstants.MESSAGE_500)
                    .statusCode(AccountsConstants.STATUS_500)
                    .build());
        }

    }
}
