package com.company.hub.ui.component;

import com.company.hub.logout.HubLogout;
import io.jmix.ui.component.mainwindow.impl.LogoutButtonImpl;

/**
 * LogoutButton that additionally performs a logout from all applications registered in the hub.
 */
public class HubLogoutButtonImpl extends LogoutButtonImpl {

    @Override
    protected void logout() {
        super.logout();
        HubLogout hubLogout = applicationContext.getBean(HubLogout.class);
        hubLogout.logoutFromAllApps();
    }
}
