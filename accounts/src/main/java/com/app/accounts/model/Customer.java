package com.app.accounts.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity{


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator = "native")
    @Column(name="customer_id")
    private Long customerId;

    private String name ;

    private String email;

    private String mobileNumber;

}
