package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class InMemoryItemRepository implements ItemRepository {

    private final Map<Integer, Item> items = new HashMap<>();
    private final Map<Integer, List<Item>> userItems = new HashMap<>();
    private Integer id = 1;

    @Override
    public Item addNewItem(Item item) {
        item.setId(id++);
        items.put(item.getId(), item);
        Integer userId = item.getOwner().getId();
        if (userItems.containsKey(userId)) {
            userItems.get(userId).add(item);
        } else {
            List<Item> itemList = new ArrayList<>();
            itemList.add(item);
            userItems.put(userId, itemList);
        }
        log.info("Добавлена новая вещь {}", item);
        return item;
    }

    @Override
    public Item updateItem(Item item, Integer itemDtoId) {
        Item oldItem = items.get(itemDtoId);
        if(item.getName() != null) oldItem.setName(item.getName());
        if(item.getDescription() != null) oldItem.setDescription(item.getDescription());
        if(item.getAvailable() != null) oldItem.setAvailable(item.getAvailable());
        log.info("Обновлены данные вещи с id = {}", item.getId());
        return oldItem;
    }

    @Override
    public Item getItemByUserId(Integer userId, Integer itemId) {
        if (userItems.get(userId) == null) return null;
        return userItems.get(userId)
                .stream()
                .filter(item -> itemId.equals(item.getId()))
                .findFirst().get();
    }

    @Override
    public Item getItemById(Integer itemDtoId) {
        return items.get(itemDtoId);
    }

    @Override
    public List<Item> getAllItemsByUserId(Integer userId) {
        return userItems.get(userId);
    }

    @Override
    public List<Item> searchItemsByText(String text) {
        return items.values()
                .stream()
                .filter(item -> isItemAvailableAndContainsText(item, text))
                .collect(Collectors.toList());
    }

    private boolean isItemAvailableAndContainsText(Item item, String text) {
        return item.getAvailable()
                && (item.getName().toLowerCase().contains(text) || item.getDescription().toLowerCase().contains(text));
    }
}
