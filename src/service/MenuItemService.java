package service;

import model.MenuItem;
import repository.MenuItemRepository;
import java.util.Optional;

public class MenuItemService {
    private MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem addMenuItem(String name, String description) {
        //Fetch MenuItem, if exists. else create it
        Optional<MenuItem> menuItemOpt = this.menuItemRepository.findByName(name);
        if(menuItemOpt.isPresent()) {
            return menuItemOpt.get();
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setDescription(description);
        return menuItemRepository.save(menuItem);
    }
}