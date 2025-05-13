package repository;

import model.MenuItem;

import java.util.*;

public class MenuItemRepository {
    private Map<Long, MenuItem> menuItemMap = new HashMap<>();
    private static long id = 1;

    public MenuItem save(MenuItem menuItem) {
        if(menuItem.getId() == 0) {
            menuItem.setId(id++);
        }
        menuItemMap.put(menuItem.getId(), menuItem);
        return menuItem;
    }

    public Optional<MenuItem> findById(long id) {
        return Optional.ofNullable(menuItemMap.get(id));
    }

    public Optional<MenuItem> findByName(String name) {
        for(MenuItem menuItem : menuItemMap.values()) {
            if(menuItem.getName().equalsIgnoreCase(name)) {
                return Optional.of(menuItem);
            }
        }
        return Optional.empty();
    }

    public List<MenuItem> findAll() {
        return new ArrayList<>(menuItemMap.values());
    }
}
