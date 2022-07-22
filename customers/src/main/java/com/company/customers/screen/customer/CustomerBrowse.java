package com.company.customers.screen.customer;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import com.company.customers.entity.Customer;

@Route("customers")
@UiController("Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}