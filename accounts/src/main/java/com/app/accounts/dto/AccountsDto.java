package com.app.accounts.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {


    @NotEmpty(message="Account Number cannot be null or empty ")
    @Pattern(regexp="(^$|[0-9]{10})",message = "account number must be 10 digits ")
    private Long accountNumber;

    @NotEmpty(message="Account type cannot be null or empty ")
    private String accountType;

    @NotEmpty(message="branch address cannot be null or empty ")
    private String branchAddress;
}
