package com.company.hub.ui.component;

import io.jmix.ui.xml.layout.loader.LogoutButtonLoader;

public class HubLogoutButtonLoader extends LogoutButtonLoader {

    @Override
    public void createComponent() {
        resultComponent = factory.create(HubLogoutButtonImpl.class);
        loadId(resultComponent, element);
    }

}
