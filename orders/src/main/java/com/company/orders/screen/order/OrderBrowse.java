package com.company.orders.screen.order;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import com.company.orders.entity.Order;

@Route("orders")
@UiController("Order_.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}