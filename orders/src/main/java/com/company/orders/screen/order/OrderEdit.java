package com.company.orders.screen.order;

import io.jmix.ui.screen.*;
import com.company.orders.entity.Order;

@UiController("Order_.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}