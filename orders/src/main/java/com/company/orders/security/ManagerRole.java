package com.company.orders.security;

import com.company.orders.entity.Order;
import com.company.orders.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Manager", code = "manager")
public interface ManagerRole {
    @MenuPolicy(menuIds = "Order_.browse")
    @ScreenPolicy(screenIds = {"Order_.browse", "Order_.edit"})
    void screens();

    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    void order();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void user();
}