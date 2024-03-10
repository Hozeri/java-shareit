package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item addNewItem(Item item);
    Item updateItem(Item item, Integer itemDtoId);
    Item getItemByUserId(Integer userId, Integer itemId);
    Item getItemById(Integer itemDtoId);
    List<Item> getAllItemsByUserId(Integer userId);
    List<Item> searchItemsByText(String text);
}
