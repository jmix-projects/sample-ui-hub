package com.company.hub.screen.main;

import com.company.hub.integration.IntegrationProperties;
import com.company.hub.integration.MenuItemDto;
import com.company.hub.integration.MenuRetriever;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.WebBrowserTools;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@UiController("MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    private static final Logger log = LoggerFactory.getLogger(MainScreen.class);

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    @Autowired
    private SideMenu sideMenu;
    @Autowired
    private WebBrowserTools webBrowserTools;

    @Autowired
    private MenuRetriever menuRetriever;
    @Autowired
    private IntegrationProperties integrationProperties;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();

        createApplicationsMenu();
    }

    private void createApplicationsMenu() {
        for (Map.Entry<String, String> entry : integrationProperties.getAppNames().entrySet()) {
            String appId = entry.getKey();
            String appName = entry.getValue();

            List<MenuItemDto> menuItems = menuRetriever.retrieveMenu(appId);

            SideMenu.MenuItem rootItem;

            rootItem = sideMenu.getMenuItem(appId);
            if (rootItem != null) {
                sideMenu.removeMenuItem(rootItem);
            }

            rootItem = sideMenu.createMenuItem(appId, appName);
            sideMenu.addMenuItem(rootItem);

            addToMenu(rootItem, menuItems, appId);
        }
    }

    private void addToMenu(SideMenu.MenuItem parentItem, List<MenuItemDto> dtoList, String appId) {
        String rootUrl = integrationProperties.getAppUrls().get(appId) + "/#main/";
        for (MenuItemDto dto : dtoList) {
            String menuItemId = appId + "-" + dto.getId();
            SideMenu.MenuItem menuItem = sideMenu.createMenuItem(menuItemId, dto.getCaption());
            if (StringUtils.isNotEmpty(dto.getPath())) {
                menuItem.setCommand(it ->
                        webBrowserTools.showWebPage(
                                rootUrl + dto.getPath(),
                                ParamsMap.of("target", "_self")));
            }

            parentItem.addChildItem(menuItem);

            addToMenu(menuItem, dto.getChildren(), appId);
        }
    }
}
