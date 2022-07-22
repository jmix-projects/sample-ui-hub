package com.company.orders.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "Worker", code = "worker")
public interface WorkerRole {
}