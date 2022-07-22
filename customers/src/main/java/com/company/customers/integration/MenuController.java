package com.company.customers.integration;

import io.jmix.core.AccessManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.WindowConfig;
import io.jmix.ui.WindowInfo;
import io.jmix.ui.accesscontext.UiMenuContext;
import io.jmix.ui.menu.MenuConfig;
import io.jmix.ui.menu.MenuItem;
import io.jmix.ui.navigation.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Autowired
    private MenuConfig menuConfig;

    @Autowired
    private WindowConfig windowConfig;

    @Autowired
    protected AccessManager accessManager;

    @GetMapping("/integration/test")
    public String test() {
        return "Hello from " + currentAuthentication.getUser().getUsername();
    }

    @GetMapping("/integration/menu")
    public List<MenuItemDto> getMenu() {
        List<MenuItemDto> dtoList = mapItemsToDto(menuConfig.getRootItems());
        return dtoList;
    }

    private List<MenuItemDto> mapItemsToDto(List<MenuItem> menuItemList) {
        List<MenuItemDto> dtoList = new ArrayList<>();
        for (MenuItem menuItem : menuItemList) {
            if (isPermitted(menuItem)) {
                MenuItemDto dto = new MenuItemDto(
                        menuItem.getId(),
                        getPath(menuItem.getScreen()),
                        menuConfig.getItemCaption(menuItem),
                        mapItemsToDto(menuItem.getChildren())
                );
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private boolean isPermitted(MenuItem menuItem) {
        UiMenuContext menuItemContext = new UiMenuContext(menuItem);
        accessManager.applyRegisteredConstraints(menuItemContext);
        return (menuItemContext.isPermitted() && !menuItem.isSeparator());
    }

    private String getPath(String screenId) {
        if (screenId == null)
            return null;
        WindowInfo windowInfo = windowConfig.getWindowInfo(screenId);
        RouteDefinition routeDefinition = windowInfo.getRouteDefinition();
        return routeDefinition == null ? null : routeDefinition.getPath();
    }
}
