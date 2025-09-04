package com.wipro.komal.billing.repository;


import com.wipro.komal.billing.entity.BillingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<BillingRecord, Long> {}
