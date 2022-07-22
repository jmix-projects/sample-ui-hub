package com.company.orders.screen.order;

import io.jmix.ui.screen.*;
import com.company.orders.entity.Order;

@UiController("Order_.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}