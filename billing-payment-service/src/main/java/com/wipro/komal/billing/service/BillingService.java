package com.wipro.komal.billing.service;



import com.wipro.komal.billing.entity.BillingRecord;

import java.util.List;

public interface BillingService {
    BillingRecord createBilling(BillingRecord record);
    BillingRecord payBilling(Long id);
    List<BillingRecord> getAll();
    BillingRecord getById(Long id);
    BillingRecord updateBilling(Long id, BillingRecord record);
    void deleteBilling(Long id);
}
