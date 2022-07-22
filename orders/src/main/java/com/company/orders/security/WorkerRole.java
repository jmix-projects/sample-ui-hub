package com.company.orders.security;

import com.company.orders.entity.Order;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Worker", code = "worker")
public interface WorkerRole {
    @MenuPolicy(menuIds = "Order_.browse")
    @ScreenPolicy(screenIds = "Order_.browse")
    void screens();

    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.READ)
    void order();
}