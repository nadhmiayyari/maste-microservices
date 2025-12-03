package com.app.accounts.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGen")
    @SequenceGenerator(name = "mySeqGen", sequenceName = "customer_sequence", allocationSize = 1)
    @Column(name="customer_id")
    private Long customerId;


    @Column(name="name")
    private String name ;

    @Column(name="email")
    private String email;

    @Column(name="mobile_number")
    private String mobileNumber;

}
