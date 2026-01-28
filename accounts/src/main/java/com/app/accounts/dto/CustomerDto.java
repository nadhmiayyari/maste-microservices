package com.app.accounts.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {


    @NotEmpty(message="name can not be null or empty")
    @Size(min=5,max=30,message="the length of the customer must be between 5 and 100")
    private String name ;



    @NotEmpty(message="email address can not be a null or empty")
    @Email(message = "email address should be a valid value")
    private String email;



    @Pattern(regexp="(^$|[0-9]{10})", message = "mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
