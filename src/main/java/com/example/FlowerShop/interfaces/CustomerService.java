package com.example.FlowerShop.interfaces;

import com.example.FlowerShop.model.AppUser;
import com.example.FlowerShop.model.Customer;
import com.example.FlowerShop.service.AppService;

public interface CustomerService extends AppService<Customer> {
    Customer getCustomerByUser(AppUser user);
}
