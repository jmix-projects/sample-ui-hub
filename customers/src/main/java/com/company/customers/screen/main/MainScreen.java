package com.company.customers.screen.main;

import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.WebBrowserTools;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HtmlAttributes;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.component.mainwindow.UserIndicator;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiControllerUtils;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("main")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

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
    private UserIndicator userIndicator;

    @Autowired
    private HtmlAttributes htmlAttributes;

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

        SideMenu.MenuItem menuItem = sideMenu.createMenuItem("go-to-hub", "Go to Hub");
        menuItem.setCommand(it ->
                webBrowserTools.showWebPage("http://host0:8080", ParamsMap.of("target", "_self")));
        sideMenu.addMenuItem(menuItem, 0);

        //since we removed the logout button the user indicator component must take more space
        htmlAttributes.applyCss(userIndicator, "flex-grow: 1");
    }
}
