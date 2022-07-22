package com.company.orders.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "Manager", code = "manager")
public interface ManagerRole {
}