package com.company.customers.integration;

import java.util.List;

public class MenuItemDto {

    private String id;
    private String path;
    private String caption;
    private List<MenuItemDto> children;

    public MenuItemDto(String id, String path, String caption, List<MenuItemDto> children) {
        this.id = id;
        this.path = path;
        this.caption = caption;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getCaption() {
        return caption;
    }

    public List<MenuItemDto> getChildren() {
        return children;
    }
}
