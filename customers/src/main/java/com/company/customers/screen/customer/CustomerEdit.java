package com.company.customers.screen.customer;

import io.jmix.ui.screen.*;
import com.company.customers.entity.Customer;

@UiController("Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}