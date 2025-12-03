package com.app.accounts.repository;

import com.app.accounts.model.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Long> {


    @Transactional
    @Modifying
    void deleteAccountsByCustomerId(Long customerId);

    Optional<Accounts> findByCustomerId(Long customerId);
}
